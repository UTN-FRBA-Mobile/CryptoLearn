from flask_smorest import Blueprint
from flask.views import MethodView
from app.controllers.log_in.models import LogInModel

from app.controllers.sign_up.models import SignUpModel
from app.jwt import JWTManager
from app.services.database.user import User

blp = Blueprint("log_in", "log_in", description="Sign up route", url_prefix="/log_in")


@blp.route("/")
class LogInResource(MethodView):
    @staticmethod
    @blp.response(200)
    @blp.arguments(LogInModel)
    def post(body_data: dict):
        user = User(body_data["email"], body_data["password"])
        if user.exist():
            return {"token": JWTManager.create_access_token(identity=user.email)}, 200
        else:
            return "", 403
