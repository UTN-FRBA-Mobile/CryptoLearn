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

class Chapter:
    def __init__(
        self,
        name: str,
        url: str,
        questions: List[Question],
        image: str,
    ) -> None:
        self.name = name
        self.url = url
        self.questions = questions
        self.image = image

    def to_json(self):
        return {
            "name": self.name,
            "url": self.url,
            "questions": list(map(lambda x: x.to_json(), self.questions)),
            "image": self.image
        }

class Level:
    def __init__(self, chapters: List[Chapter]) -> None:
        self.chapters = chapters

    def to_json(self):
        return {
            "chapters": list(map(lambda x: x.to_json(), self.chapters)),
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

Chapter_1 = Chapter(
    "Capitulo 1",
    "https://www.newscientist.com/definition/bitcoin",
    [Question("Que es el bitcoin?", ["Un gusto de helado", "Una criptomoneda"], 1)],
    "chapter_1"
)

Chapter_2 = Chapter(
    "Capitulo 2",
    "https://www.newscientist.com/definition/bitcoin",
    [
        Question("Que es el usdt?", ["Un gusto de helado", "Una criptomoneda"], 1),
        Question("Que es el etherium?", ["Un gusto de helado", "Una criptomoneda"], 1)
    ],
    "chapter_2"
)

Level_1 = Level([
    Chapter_1,
    Chapter_2,
])

levels_by_user = {
    "admin1@gmail.com": [
        LevelsByUser(Level_1, User("admin1@gmail.com", ""), "en-curso"),
    ],
    "admin2@gmail.com": [
        LevelsByUser(Level_1, User("admin2@gmail.com", ""), "completado"),
    ],
}
