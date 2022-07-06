import hashlib
from app.services.database import BaseModel


class User(BaseModel):
    def __init__(self, email: str, password: str) -> None:
        self.email = email
        self.password = hashlib.md5(password.encode()).hexdigest()
        super().__init__()

    def save(self):
        # db.init_transaccion()
        # db.insert_user(self)
        # db.commit()
        return 1

    def exist(self):
    	return self.email in users


users = ["admin1@gmail.com", "admin2@gmail.com"]
