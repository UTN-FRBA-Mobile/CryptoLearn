"""Flask smorest Api manager"""

from flask import Flask
from flask_smorest import Api, Blueprint

from app.global_context import get_logger


class ApiManager:
    __api = Api()

    @classmethod
    def init_app(cls, app: Flask) -> None:
        get_logger().debug("Initializing flask_smorest")

        cls.__api.init_app(app)

    @classmethod
    def register_blueprint(cls, blp: Blueprint) -> None:
        get_logger().debug("Registering %s", blp.name)
        cls.__api.register_blueprint(blp)
