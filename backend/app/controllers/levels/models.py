from typing import List

import marshmallow


class Question:
    def __init__(self, question: str, options: List[str], answer: str) -> None:
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
        return self.state == "done"

    def to_json(self):
        # random.shuffle(self.questions)
        return {
            "name": self.name,
            "url": self.url,
            "questions": list(map(lambda x: x.to_json(), self.questions)),
            "image": self.image,
            "state": self.state,
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
    "Capítulo 1 - Bitcoin",
    "http:/192.168.0.105:8080/static/bitcoin.html",
    [
        Question(
            "¿Qué es Bitcoin?",
            ["Un juego", "Una criptomoneda", "Un programa de PC"],
            "Una criptomoneda",
        ),
        Question(
            "¿Cuándo fue creado Bitcoin?",
            ["2009", "2014", "2005"],
            "2009",
        ),
        Question(
            "¿Cuántas millones de unidades de Bitcoin existen?",
            ["9", "43", "21"],
            "21",
        ),
    ],
    "chapter_1",
    "in_progress",
)

Chapter_2 = Chapter(
    "Capítulo 2 - Stables",
    "http:/192.168.0.105:8080/static/usdt.html",
    [
        Question(
            "¿1.00 dolares siempre seran iguales a 1.00 USDT/USDC/DAI?",
            ["Verdadero, nunca cambia", "Falso, puede variar levemente, o mucho en casos extremos"],
            "Falso, puede variar levemente, o mucho en casos extremos",
        ),
        Question(
            "¿Cuál stablecoin es descentralizada?",
            ["USDT", "USDC", "DAI"],
            "DAI",
        ),
        Question(
            "¿Cuál stablecoin respalda cada activo con un dolar fisico?",
            ["USDT", "USDC", "DAI"],
            "USDC",
        ),
    ],
    "chapter_2",
    "in_progress",
)

Chapter_3 = Chapter(
    "Capítulo 3 - Ethereum",
    "http:/192.168.0.105:8080/static/ethereum.html",
    [
        Question(
            "¿Ethereum tiene un ecosistema centralizado?",
            ["Verdadero", "Falso"],
            "Falso",
        ),
        Question(
            "¿Como se llama la moneda de cambio que utiliza Ethereum?",
            ["Ethereum", "Ether", "Eth"],
            "Ether",
        ),
        Question(
            "¿La cadena de Ethereum se centra en darle seguimiento a los Ethers?",
            ["Verdadero, es su unico proposito", "Falso, dan mas importancia a ejecutar apps descentralizadas"],
            "Falso, dan mas importancia a ejecutar apps descentralizadas",
        ),
    ],
    "chapter_3",
    "in_progress",
)

Chapter_4 = Chapter(
    "Capítulo 4",
    "http:/192.168.0.105:8080/static/bitcoin.html",
    [
        Question(
            "¿Qué es el bitcoin?",
            ["Un modelo de teclado", "Una criptomoneda"],
            "Una criptomoneda",
        ),
        Question(
            "¿Qué es etherium?", ["Un animal", "Una criptomoneda"], "Una criptomoneda"
        ),
    ],
    "chapter_2",
    "block",
)

Chapter_5 = Chapter(
    "Capítulo 5",
    "http:/192.168.0.105:8080/static/bitcoin.html",
    [
        Question(
            "¿Qué es el bitcoin?",
            ["Un modelo de teclado", "Una criptomoneda"],
            "Una criptomoneda",
        ),
        Question(
            "¿Qué es etherium?", ["Un animal", "Una criptomoneda"], "Una criptomoneda"
        ),
    ],
    "chapter_3",
    "block",
)

Chapter_6 = Chapter(
    "Capítulo 6",
    "http:/192.168.0.105:8080/static/bitcoin.html",
    [
        Question(
            "¿Qué es el bitcoin?",
            ["Un modelo de teclado", "Una criptomoneda"],
            "Una criptomoneda",
        ),
        Question(
            "¿Qué es etherium?", ["Un animal", "Una criptomoneda"], "Una criptomoneda"
        ),
    ],
    "chapter_1",
    "block",
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
                chapter.state = "in_progress"
        except IndexError:
            pass


def update_chapter_state(email: str, level_index: int, chapter_index: int, state: str):
    assert state == "done" or state == "in_progress" or state == "block"
    levels_by_user[email][level_index].chapters[chapter_index].state = state
    enable_next_level(email, level_index)


class State(marshmallow.Schema):
    state: str = marshmallow.fields.Str()
