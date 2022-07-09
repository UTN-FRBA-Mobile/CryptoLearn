from typing import List

import marshmallow


class Question:
    def __init__(
        self, question: str, options: List[str], answer: str
    ) -> None:
        self.question = question
        self.options = options
        self.answer = answer

    def get_anser_index(self) -> int:
        return self.options.index(self.answer)

    def to_json(self):
        return {
            "questionTitle": self.question,
            "options": self.options,
            "answer": self.answer,
            "answerIndex": self.get_anser_index(),
        }


class Chapter:
    def __init__(
        self, name: str, url: str, questions: List[Question], image: str, state: str
    ) -> None:
        self.name = name
        self.url = url
        self.questions = questions
        self.image = image
        self.state = state

    def is_done(self):
        return self.state == 'done'

    def to_json(self):
        # random.shuffle(self.questions)
        return {
            "name": self.name,
            "url": self.url,
            "questions": list(map(lambda x: x.to_json(), self.questions)),
            "image": self.image,
            "state": self.state
        }


class Level:
    def __init__(self, chapters: List[Chapter]) -> None:
        self.chapters = chapters

    def is_done(self):
        return all(list(map(lambda chapter: chapter.is_done(), self.chapters)))

    def to_json(self):
        return {
            "chapters": list(map(lambda x: x.to_json(), self.chapters)),
        }


Chapter_1 = Chapter(
    "Capitulo 1",
    "https://www.newscientist.com/definition/bitcoin",
    [
        Question(
            "Que es el bitcoin?",
            ["Un gusto de helado", "Una criptomoneda", "ASDADASD"],
            "Una criptomoneda"
        ),
        Question(
            "Que es etherium?",
            ["Una criptomoneda", "Un noticiero"],
            "Una criptomoneda"
        ),
        Question(
            "Que es dogecoin?",
            ["Una raza de perro", "Una criptomoneda"],
            "Una criptomoneda"
        ),
    ],
    "chapter_1",
    "in-progress"
)

Chapter_2 = Chapter(
    "Capitulo 2",
    "https://www.newscientist.com/definition/bitcoin",
    [
        Question(
            "Que es el usdt?",
            ["Un gusto de helado", "Una criptomoneda"],
            "Una criptomoneda"
        ),
        Question(
            "Que es etherium?",
            ["Un gusto de helado", "Una criptomoneda"],
            "Una criptomoneda"
        ),
    ],
    "chapter_2",
    "in-progress"
)

Chapter_3 = Chapter(
    "Capitulo 3",
    "https://www.newscientist.com/definition/bitcoin",
    [
        Question(
            "Que es el bitcoin?",
            ["Un modelo de teclado", "Una criptomoneda"],
            "Una criptomoneda"
        ),
        Question(
            "Que es etherium?",
            ["Un animal", "Una criptomoneda"],
            "Una criptomoneda"
        ),
    ],
    "chapter_3",
    "in-progress"
)

Chapter_4 = Chapter(
    "Capitulo 4",
    "https://www.newscientist.com/definition/bitcoin",
    [
        Question(
            "Que es el bitcoin?",
            ["Un modelo de teclado", "Una criptomoneda"],
            "Una criptomoneda"
        ),
        Question(
            "Que es etherium?",
            ["Un animal", "Una criptomoneda"],
            "Una criptomoneda"
        ),
    ],
    "chapter_2",
    "block"
)

Chapter_5 = Chapter(
    "Capitulo 5",
    "https://www.newscientist.com/definition/bitcoin",
    [
        Question(
            "Que es el bitcoin?",
            ["Un modelo de teclado", "Una criptomoneda"],
            "Una criptomoneda"
        ),
        Question(
            "Que es etherium?",
            ["Un animal", "Una criptomoneda"],
            "Una criptomoneda"
        ),
    ],
    "chapter_3",
    "block"
)

Chapter_6 = Chapter(
    "Capitulo 6",
    "https://www.newscientist.com/definition/bitcoin",
    [
        Question(
            "Que es el bitcoin?",
            ["Un modelo de teclado", "Una criptomoneda"],
            "Una criptomoneda"
        ),
        Question(
            "Que es etherium?",
            ["Un animal", "Una criptomoneda"],
            "Una criptomoneda"
        ),
    ],
    "chapter_1",
    "block"
)

Level_1 = Level(
    [
        Chapter_1,
        Chapter_2,
        Chapter_3,
    ]
)
Level_2 = Level([Chapter_4, Chapter_5])
Level_3 = Level([Chapter_6])
levels_by_user = {
    "admin1@gmail.com": [Level_1, Level_2, Level_3],
    "admin2@gmail.com": [Level_1],
}


def enable_next_level(email, level_index):
    if levels_by_user[email][level_index].is_done():
        try:
            for chapter in levels_by_user[email][level_index + 1].chapters:
                chapter.state = "in-progress"
        except IndexError:
            pass


def update_chapter_state(
    email: str,
    level_index: int,
    chapter_index: int,
    state: str
):
    assert state == "done" or state == "in-progress" or state == "block"
    levels_by_user[email][level_index].chapters[chapter_index].state = state
    enable_next_level(email, level_index)


class State(marshmallow.Schema):
    state: str = marshmallow.fields.Str()
