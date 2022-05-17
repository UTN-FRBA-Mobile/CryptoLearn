import marshmallow


class LogInModel(marshmallow.Schema):
    email = marshmallow.fields.Str()
    password = marshmallow.fields.Str()
