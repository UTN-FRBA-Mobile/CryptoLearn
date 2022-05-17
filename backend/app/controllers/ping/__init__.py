from flask_smorest import Blueprint
from flask.views import MethodView

blp = Blueprint("ping", "ping", description="Ping route")


@blp.route("/ping")
class PingResource(MethodView):
    """Ping resource."""

    @staticmethod
    @blp.response(200, example="Pong")
    def get():
        return "Pong", 200
