package com.example.mvvm.data

import app.cash.turbine.test
import com.example.mvvm.data.retrofit.TMDBMovieDataSource
import com.example.mvvm.data.room.RoomMovieDataSource
import com.example.mvvm.data.room.ScreenResponseEntity
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class MovieRepositoryTest {
    @MockK
    lateinit var roomDBDataSource: RoomMovieDataSource

    @MockK
    lateinit var tmdbDataSource: TMDBMovieDataSource

    @MockK
    val moshi = mockk<Moshi>()

    @MockK
    val adapter = mockk<JsonAdapter<List<Row>>>()

    @OptIn(ExperimentalCoroutinesApi::class)
    val iodispatcher = UnconfinedTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    val defaultdispatcher = UnconfinedTestDispatcher()
    lateinit var repository: MovieRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        every {
            moshi.adapter<List<Row>>(
                Types.newParameterizedType(List::class.java, Row::class.java),
            )
        } returns adapter
        repository =
            MovieRepository(
                roomDBDataSource,
                tmdbDataSource,
                iodispatcher,
                defaultdispatcher,
                moshi,
            )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `given local cache is empty when loading home page, then should return empty list`() =
        runTest {
            every { roomDBDataSource.getScreenResponse(any()) } returns flowOf(null)

            val result = repository.observeCachedHomePage()

            result.take(1).test {
                val item = awaitItem()
                assert(item.isSuccess)
                assert(item.getOrNull()?.isEmpty() == true)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `given local cache exists when loading home page, then should return cached data`() =
        runTest {
            val cachedHomeJson =
                """
                [{
                	screenName: "home",
                	responseJson: "{
                		header: \"Watch List\",
                		titleList: [
                			{
                				id: 1,
                				title: \"Sisu: Road to Revenge\",
                				imageUrl: \"/gDVgC9jd917NdAcqBdRRDUYi4Tq.jpg\"
                			},
                			{
                				id: 2,
                				title: \"Good Boy\",
                				imageUrl: \"/pvMHRi09ur2L1drXh2dXFtuMFgl.jpg\"
                			}
                		]
                	}",
                	updated_at: 1766287478
                }]
                """.trimIndent()
            every { roomDBDataSource.getScreenResponse(any()) } returns flowOf(cachedHomeJson)
            every { adapter.fromJson(cachedHomeJson) } returns provideMockRow()

            val result = repository.observeCachedHomePage()

            result.take(1).test {
                val item = awaitItem()
                assert(item.isSuccess)
                val rowList = item.getOrNull()!!
                assert(rowList.size == 1)
                assert(rowList[0].header == "Watch List")
                assert(rowList[0].titleList[0].title == "Sisu: Road to Revenge")
                assert(rowList[0].titleList[1].title == "Good Boy")
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `given remote API returns successfully then should cache the response`() =
        runTest {
            coEvery { tmdbDataSource.getHomePage() } returns provideMockRow()
            coEvery { roomDBDataSource.upsertScreenResponse(any<ScreenResponseEntity>()) } just Runs
            every { adapter.toJson(any<List<Row>>()) } returns ""

            repository.refreshHomepageIfNeeded()
            verify { roomDBDataSource.upsertScreenResponse(any<ScreenResponseEntity>()) }
        }

    @Test
    fun `given remote API returns error then should not cache the response and fail silently`() =
        runTest {
            val response = Response.error<String>(404, okhttp3.ResponseBody.create(null, "Not Found"))
            coEvery { tmdbDataSource.getHomePage() } throws HttpException(response)

            coEvery { roomDBDataSource.upsertScreenResponse(any<ScreenResponseEntity>()) } just Runs
            every { adapter.toJson(any<List<Row>>()) } returns ""

            repository.refreshHomepageIfNeeded()
            verify(exactly = 0) { roomDBDataSource.upsertScreenResponse(any<ScreenResponseEntity>()) }
        }

    private fun provideMockRow(): List<Row> {
        return listOf(
            Row(
                "Watch List",
                listOf(
                    RowTitle(id = 1, title = "Sisu: Road to Revenge", "/gDVgC9jd917NdAcqBdRRDUYi4Tq.jpg"),
                    RowTitle(id = 2, title = "Good Boy", "/pvMHRi09ur2L1drXh2dXFtuMFgl.jpg"),
                ),
            ),
        )
    }
}
