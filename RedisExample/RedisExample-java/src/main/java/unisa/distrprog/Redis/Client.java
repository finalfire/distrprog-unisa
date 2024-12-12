package unisa.distrprog.Redis;

import redis.clients.jedis.UnifiedJedis;

public class Client {
    public static void main(String[] args) {
        UnifiedJedis jedis = new UnifiedJedis("redis://localhost:6379");

        // Usually key:n is used to define a "key namespace" in order
        // to avoid collision with keys having the same name
        String res1 = jedis.set("foo:1", "Bar");
        // Printing res1 returns an acknowledgment (OK) on the set of the key
        System.out.println(res1);

        String res2 = jedis.get("foo:1");
        // Prints "Bar"
        System.out.println(res2);

        // Using Lists
        jedis.lpush("tasks", "task1", "task2");
        System.out.println(jedis.lpop("tasks")); // Output: task2

        // Working with Hashes
        jedis.hset("user:1", "name", "Bar");
        jedis.hset("user:1", "age", "25");
        System.out.println(jedis.hget("user:1", "name")); // Output: Bob

        // Leaderboard with Sorted Sets
        jedis.zadd("leaderboard", 50, "Foo");
        jedis.zadd("leaderboard", 70, "Bar");
        System.out.println(jedis.zrangeWithScores("leaderboard", 0, -1));

        jedis.close();
    }
}