import logging
from flask import has_app_context, current_app


def get_logger() -> logging.Logger:  # pragma: no cover
    if has_app_context():  # type: ignore
        return current_app.logger  # type: ignore

    # redundancia a la de __init__.py
    logger = logging.Logger("Jaimito")
    logger.handlers.clear()
    logger.setLevel(logging.INFO)

    return logger
