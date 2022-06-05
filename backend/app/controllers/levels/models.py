import json
from typing import List

from app.services.database.user import User


class Question:
    def __init__(self, question: str, options: List[str], answer: int) -> None:
        self.question = question
        self.options = options
        self.answer = answer

    def to_json(self):
        return {
            "question": self.question,
            "options": self.options,
            "answer": self.answer,
        }


class Level:
    def __init__(
        self,
        name: str,
        url: str,
        questions: List[Question],
    ) -> None:
        self.name = name
        self.url = url
        self.questions = questions

    def to_json(self):
        return {
            "name": self.name,
            "url": self.url,
            "questions": list(map(lambda x: x.to_json(), self.questions)),
        }


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
    [Question("Que es el bitcoin?", ["Un gusto de helado", "Una criptomoneda"], 1)],
)

Question_2_1 = Level(
    "Capitulo 2",
    "http://localhost:8080/levels/2/1",
    [Question("Que es el usdt?", ["Un gusto de helado", "Una criptomoneda"], 1)],
)
Question_2_2 = Level(
    "Capitulo 2",
    "http://localhost:8080/levels/2/2",
    [Question("Que es el etherium?", ["Un gusto de helado", "Una criptomoneda"], 1)],
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
