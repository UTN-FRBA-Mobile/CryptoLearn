"""Flask jwt manager"""

import datetime
from typing import Any
import flask_jwt_extended as flask_jwt
from flask import Flask


class JWTManager:
    """Flask jwt manager"""

    _jwt = flask_jwt.JWTManager()

    @classmethod
    def init_app(cls, app: Flask) -> None:
        """Initialize flask_jwt"""

        cls._jwt.init_app(app)

    @classmethod
    def create_access_token(
        cls,
        identity: Any,
        fresh: datetime.timedelta = False,
        expires_delta: datetime.timedelta = False,  # si esta en flase no expira
        additional_claims: Any = None,
        additional_headers: Any = None,
    ) -> str:
        """
        Create a new access token.

        :param identity:
            The identity of this token. It can be any data that is json serializable.
            You can use :meth:`~flask_jwt_extended.JWTManager.user_identity_loader`
            to define a callback function to convert any object passed in into a json
            serializable format.

        :param fresh:
            If this token should be marked as fresh, and can thus access endpoints
            protected with ``@jwt_required(fresh=True)``. Defaults to ``False``.

            This value can also be a ``datetime.timedelta``, which indicate
            how long this token will be considered fresh.

        :param expires_delta:
            A ``datetime.timedelta`` for how long this token should last before it
            expires. Set to False to disable expiration. If this is None, it will use
            the ``JWT_ACCESS_TOKEN_EXPIRES`` config value (see :ref:`Configuration Options`)

        :param additional_claims:
            Optional. A hash of claims to include in the access token.  These claims are
            merged into the default claims (exp, iat, etc) and claims returned from the
            :meth:`~flask_jwt_extended.JWTManager.additional_claims_loader` callback.
            On conflict, these claims take presidence.

        :param headers:
            Optional. A hash of headers to include in the access token. These headers
            are merged into the default headers (alg, typ) and headers returned from
            the :meth:`~flask_jwt_extended.JWTManager.additional_headers_loader`
            callback. On conflict, these headers take presidence.

        :return:
            An encoded access token
        """
        return flask_jwt.create_access_token(
            identity, fresh, expires_delta, additional_claims, additional_headers
        )
