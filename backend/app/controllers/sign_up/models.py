import marshmallow


class SignUpModel(marshmallow.Schema):
    name = marshmallow.fields.Str()
    last_name = marshmallow.fields.Str()
    email = marshmallow.fields.Str()
    password = marshmallow.fields.Str()
