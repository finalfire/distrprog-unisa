from fastapi import FastAPI, HTTPException, Depends
from sqlalchemy.orm import Session
from models import SessionLocal, Order
from pydantic import BaseModel

app = FastAPI()

# Pydantic model for creating an order
class OrderCreate(BaseModel):
    customer: str
    description: str
    price: float

# Dependency to get database session
def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()

# Routes

@app.post("/orders/", response_model=dict)
def create_order(order: OrderCreate, db: Session = Depends(get_db)):
    """
    Create a new order.
    """
    new_order = Order(
        customer=order.customer,
        description=order.description,
        price=order.price,
    )
    db.add(new_order)
    db.commit()
    db.refresh(new_order)
    return {"message": "Order created", "order_id": new_order.id}

@app.get("/orders/")
def list_orders(db: Session = Depends(get_db)):
    """
    Retrieve all orders.
    """
    orders = db.query(Order).all()
    return orders

@app.get("/orders/{order_id}")
def get_order(order_id: int, db: Session = Depends(get_db)):
    """
    Retrieve a single order by ID.
    """
    order = db.query(Order).filter(Order.id == order_id).first()
    if not order:
        raise HTTPException(status_code=404, detail="Order not found")
    return order

@app.delete("/orders/{order_id}")
def delete_order(order_id: int, db: Session = Depends(get_db)):
    """
    Delete an order by ID.
    """
    order = db.query(Order).filter(Order.id == order_id).first()
    if not order:
        raise HTTPException(status_code=404, detail="Order not found")
    db.delete(order)
    db.commit()
    return {"message": f"Order {order_id} deleted successfully"}
