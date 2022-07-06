import marshmallow


class ResetPasswordModel(marshmallow.Schema):
    email = marshmallow.fields.Str()
