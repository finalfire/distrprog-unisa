from fasthtml.common import *
css = Style('body { font-size: 3em; color: grey }' + 
            ' .container { background-color: grey; color: white }')
app = FastHTML(hdrs=(css,))

@app.route("/")
def get():
    return (Title("Hello World"), 
            Main(H1('Hello, World'), cls="container"))

serve()