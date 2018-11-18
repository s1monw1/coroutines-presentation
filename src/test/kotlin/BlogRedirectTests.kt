import org.junit.Test
import java.net.HttpURLConnection
import java.net.URL
import kotlin.test.assertEquals

fun getRedirectUri(orig: String): String {
    return (URL(orig).openConnection() as HttpURLConnection).run {
        instanceFollowRedirects = false
        println(responseCode)
        headerFields["Location"]?.get(0) ?: "noredirect"
    }
}

class BlogRedirectTests {

    val old = "blog.simon-wirtz.de/"
    val new = "kotlinexpertise.com/"
    val https = "https://"
    val http = "http://"
    val www = "www."
    val aboutme = "about-me/"

    @Test
    fun testRedirects() {
        assertEquals(https + new, getRedirectUri(http + old))
        assertEquals(https + new, getRedirectUri(http + www + old))
        assertEquals(https + new, getRedirectUri(http + new))
        //assertEquals(https + new, getRedirectUri(http + www + new))
        assertEquals(https + new, getRedirectUri(https + old))
        assertEquals("noredirect", getRedirectUri(https + new))
        assertEquals(https + new + aboutme, getRedirectUri(http + new + aboutme))
        assertEquals("noredirect", getRedirectUri(https + new + aboutme))
        assertEquals(https + new + aboutme, getRedirectUri(http + old + aboutme))
        assertEquals(https + new + aboutme, getRedirectUri(https + old + aboutme))
    }

}