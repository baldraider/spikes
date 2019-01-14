package com.novoda.movies.gallery

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.ktor.http.Url
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GalleryPresenterTest {

    private val view: GalleryPresenter.View = mock()
    private val fetcher: GalleryFetcher = mock()

    private val presenter: GalleryPresenter = GalleryPresenter(uiContext = Unconfined, galleryFetcher = fetcher)

    @Test
    fun `renders gallery when data available`() = runBlocking {
        val expectedGalley = Gallery(listOf(MoviePoster(1L, Url("http://www.google.com"))))
        whenever(fetcher.fetchGallery()).thenReturn(expectedGalley)

        presenter.startPresenting(view)

        verify(view).render(expectedGalley)
    }

    @Test
    fun `renders error message when fetching gallery fails`() = runBlocking<Unit> {
        whenever(fetcher.fetchGallery()).thenThrow(IllegalStateException("Something went wrong"))

        presenter.startPresenting(view)

        verify(view).renderError("Something went wrong")
    }
}
