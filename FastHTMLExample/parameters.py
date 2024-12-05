from fasthtml.common import *
app = FastHTML()

@app.get("/greet/{nm}")
def greet(nm:str):
    return f"Good day to you, {nm}!"

serve()