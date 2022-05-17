from flask_smorest import Blueprint
from flask.views import MethodView
from app.controllers.log_in.models import LogInModel
from app.controllers.sign_up.models import SignUpModel

from app.services.database.user import User

blp = Blueprint(
    "sign_up", "sign_up", description="Sign up route", url_prefix="/sign_up"
)


@blp.route("/")
class SignUpResource(MethodView):
    @staticmethod
    @blp.response(200)
    @blp.arguments(SignUpModel)
    def post(body_data: dict):
        user = User(body_data["email"], body_data["password"])
        user.save()
        return "", 201
