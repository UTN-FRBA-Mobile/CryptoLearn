from typing import List

from app.services.database.user import User


class Level:
    def __init__(
        self,
        name: str,
        url: str,
        question: str,
        options: List[str],
        answer: int,
    ) -> None:
        self.name = name
        self.url = url
        self.question = question
        self.options = options
        self.answer = answer

    def to_json(self):
        return {
            "name": self.name,
            "url": self.url,
            "question": self.question,
            "options": self.options,
            "answer": self.answer,
        }

    def to_html(self):
        return f"""
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>{self.name}</title>
</head>
<body>
    <h1>{self.question}</h1>
    <li>{self.options[0]}</li>
    <li>{self.options[1]}</li>    
</body>
</html>
"""


class LevelsByUser:
    def __init__(self, level: Level, user: User, state: str) -> None:
        self.level = level
        self.user = user
        self.state = state

    @staticmethod
    def get_levels_by_user(email: str):
        return levels_by_user.get(email, [])

    def to_json(self):
        rta = self.level.to_json()
        rta["state"] = self.state
        return rta


Question_1_1 = Level(
    "Capitulo 1",
    "http://localhost:8080/levels/1/1",
    "Que es el bitcoin?",
    ["Un gusto de helado", "Una criptomoneda"],
    0,
)

Question_2_1 = Level(
    "Capitulo 2",
    "http://localhost:8080/levels/2/1",
    "Que es el usdt?",
    ["Un gusto de helado", "Una criptomoneda"],
    0,
)
Question_2_2 = Level(
    "Capitulo 2",
    "http://localhost:8080/levels/2/2",
    "Que es el etherium?",
    ["Un gusto de helado", "Una criptomoneda"],
    0,
)

levels_by_user = {
    "admin1@gmail.com": [
        LevelsByUser(Question_1_1, User("admin1@gmail.com", ""), "completado"),
        LevelsByUser(Question_2_1, User("admin1@gmail.com", ""), "en-curso"),
        LevelsByUser(Question_2_2, User("admin1@gmail.com", ""), "no-iniciado"),
    ],
    "admin2@gmail.com": [
        LevelsByUser(Question_1_1, User("admin2@gmail.com", ""), "completado"),
        LevelsByUser(Question_2_1, User("admin2@gmail.com", ""), "completado"),
        LevelsByUser(Question_2_2, User("admin2@gmail.com", ""), "completado"),
    ],
}
