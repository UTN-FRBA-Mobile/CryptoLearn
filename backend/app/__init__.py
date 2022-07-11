import os
from flask import Flask
from app.config import BaseConfig
from app.controllers.ping import blp as ping_blp
from app.controllers.sign_up import blp as sign_up_blp
from app.controllers.log_in import blp as log_in_blp
from app.controllers.reset_password import blp as reset_password_blp
from app.controllers.levels import blp as levels_blp
from app.api import ApiManager
from app.jwt import JWTManager


def create_app() -> Flask:
    """Create Flask app."""
    app = Flask(__name__, static_folder="../static")
    app.config.from_object(BaseConfig())
    with app.app_context():
        # accepts both /endpoint and /endpoint/ as valid URLs
        app.url_map.strict_slashes = False
        JWTManager.init_app(app)
        ApiManager.init_app(app)

        register_blueprints()

    return app


def register_blueprints() -> None:
    """Register application blueprints"""

    ApiManager.register_blueprint(ping_blp)
    ApiManager.register_blueprint(sign_up_blp)
    ApiManager.register_blueprint(log_in_blp)
    ApiManager.register_blueprint(reset_password_blp)
    ApiManager.register_blueprint(levels_blp)
