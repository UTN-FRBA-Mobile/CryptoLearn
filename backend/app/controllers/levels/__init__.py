from flask import Response, render_template
from flask_smorest import Blueprint
from flask.views import MethodView
from app.controllers.levels.models import (
    LevelsByUser,
    Question_1_1,
    Question_2_1,
    Question_2_2,
)
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
            "name": "Capitulo 1",
            "options": ["Un gusto de helado", "Una criptomoneda"],
            "question": "Que es el bitcoin?",
            "state": "completado",
            "url": "http://localhost:8080/levels/1/1",
        },
    )
    def get():
        email = flask_jwt.get_jwt_identity()
        print(flask_jwt.get_jwt_identity())
        levels = LevelsByUser.get_levels_by_user(email)
        response = list(map(lambda x: x.to_json(), levels))
        return response, 200


@blp.route("/<chapter>/<question>")
class LevelsViewResource(MethodView):
    @staticmethod
    # @flask_jwt.jwt_required()
    @blp.response(200, example="<HTML>...")
    def get(chapter: str, question: str):
        chapter, question = int(chapter), int(question)
        if chapter == 1:
            return Response(
                Question_1_1.to_json(), content_type="application/json", status=200
            )
        else:
            if question == 1:
                return Response(
                    Question_2_1.to_json(), content_type="application/json", status=200
                )
            else:
                return Response(
                    Question_2_2.to_json(), content_type="application/json", status=200
                )
