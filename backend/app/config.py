class BaseConfig:
    """Base configuration."""

    DEBUG = True
    TESTING = False

    API_TITLE = "CryptoLear"
    API_VERSION = "1.0.0"
    OPENAPI_VERSION = "3.0.2"
    OPENAPI_JSON_PATH = "api-spec.json"
    OPENAPI_URL_PREFIX = "/"
    OPENAPI_SWAGGER_UI_PATH = "/swagger-ui"
    OPENAPI_SWAGGER_UI_URL = "https://cdn.jsdelivr.net/npm/swagger-ui-dist/"
    SECRET_KEY = "super-secret"
