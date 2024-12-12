import redis

if __name__ == '__main__':
    # Connect to Redis server
    r = redis.Redis(host='localhost', port=6379, decode_responses=True)

    # Set and Get Example
    r.set("name", "Foo")
    print(r.get("name"))

    # Push and pop to a list
    r.lpush("tasks", "task1", "task2")
    print(r.lpop("tasks"))

    # Set multiple fields in a hash
    r.hset("user:1", mapping={"name": "Foo", "age": 35})
    print(r.hget("user:1", "name"))

    # Add scores for players
    r.zadd("leaderboard", {"Foo": 50, "Bar": 70})
    print(r.zrange("leaderboard", 0, -1, withscores=True))