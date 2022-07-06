from flask_smorest import Blueprint
from flask.views import MethodView
from app.controllers.reset_password.models import ResetPasswordModel

from app.services.database.user import User

blp = Blueprint(
    "reset_password", "reset_password", description="Reset password route", url_prefix="/reset_password"
)


@blp.route("/")
class SignUpResource(MethodView):
    @staticmethod
    @blp.response(200)
    @blp.arguments(ResetPasswordModel)
    def post(body_data: dict):
        user = User(body_data["email"], "")
        if user.exist():
            return "", 200
        else:
            return "", 403
