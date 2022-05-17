import marshmallow


class SignUpModel(marshmallow.Schema):
    email = marshmallow.fields.Str()
    password = marshmallow.fields.Str()
