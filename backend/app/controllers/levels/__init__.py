from flask import jsonify
from flask_smorest import Blueprint
from flask.views import MethodView
from app.controllers.levels.models import State, levels_by_user, update_chapter_state
import flask_jwt_extended as flask_jwt

blp = Blueprint("levels", "levels", description="Sign up route", url_prefix="/levels")


@blp.route("/")
class LevelsResource(MethodView):
    @staticmethod
    @flask_jwt.jwt_required()
    @blp.response(
        200,
        example={
            "answer": 0,
            "name": "Capítulo 1",
            "options": ["Un gusto de helado", "Una criptomoneda"],
            "question": "Que es el bitcoin?",
            "state": "completado",
            "url": "http://localhost:8080/levels/1/1",
        },
    )
    def get():
        email = flask_jwt.get_jwt_identity()
        levels = levels_by_user.get(email, [])
        response = list(map(lambda x: x.to_json(), levels))
        return jsonify(response=response), 200


@blp.route("/<level_index>/<chapter_index>")
class LevelsUpdateResource(MethodView):
    @flask_jwt.jwt_required()
    @blp.arguments(State)
    def post(self, body, level_index, chapter_index):
        email = flask_jwt.get_jwt_identity()
        update_chapter_state(email, int(level_index), int(chapter_index), body["state"])
        return "", 200
