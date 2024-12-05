from fasthtml.common import *
app = FastHTML()

fake_db = [{"name": "Foo"}, {"name": "Bar"}]

@app.get("/items/")
def read_item(idx:int|None = 0):
    return fake_db[idx]

serve()