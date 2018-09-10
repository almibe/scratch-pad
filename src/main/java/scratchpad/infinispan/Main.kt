package scratchpad.infinispan

import org.infinispan.configuration.cache.ConfigurationBuilder
import org.infinispan.manager.DefaultCacheManager

data class Game(val title: String)

fun main(args: Array<String>) {
    // Construct a simple local cache manager with default configuration
    val cacheManager = DefaultCacheManager()
    // Define local cache configuration
    cacheManager.defineConfiguration("games", ConfigurationBuilder().build())
    println(cacheManager.definedCacheConfigurationNames.contains("games"))
    // Obtain the local cache
    val cache = cacheManager.getCache<String, Game>("games")
    // Store a value
    cache["key"] = Game("Game Title")
    // Retrieve the value and print it out
    System.out.printf("key = %s\n", cache["key"])
    // Stop the cache manager and release all resources
    cacheManager.stop()
}
