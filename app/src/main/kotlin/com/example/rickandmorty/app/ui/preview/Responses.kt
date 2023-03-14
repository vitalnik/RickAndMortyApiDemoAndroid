package com.example.rickandmorty.app.ui.preview

class Responses {

    val episodes = """
    {
        "info": {
        "count": 51,
        "pages": 3,
        "next": "https://rickandmortyapi.com/api/episode/?page=2",
        "prev": null
    },
        "results": [
        {
            "id": 1,
            "name": "Pilot",
            "air_date": "December 2, 2013",
            "episode": "S01E01",
            "characters": [
            "https://rickandmortyapi.com/api/character/1",
            "https://rickandmortyapi.com/api/character/2",
            "https://rickandmortyapi.com/api/character/35",
            "https://rickandmortyapi.com/api/character/38",
            "https://rickandmortyapi.com/api/character/62",
            "https://rickandmortyapi.com/api/character/92",
            "https://rickandmortyapi.com/api/character/435"
            ],
            "url": "https://rickandmortyapi.com/api/episode/1",
            "created": "2017-11-10T12:56:33.798Z"
        },
        {
            "id": 2,
            "name": "Lawnmower Dog",
            "air_date": "December 9, 2013",
            "episode": "S01E02",
            "characters": [
            "https://rickandmortyapi.com/api/character/305",
            "https://rickandmortyapi.com/api/character/306",
            "https://rickandmortyapi.com/api/character/329",
            "https://rickandmortyapi.com/api/character/338",
            "https://rickandmortyapi.com/api/character/396",
            "https://rickandmortyapi.com/api/character/397",
            "https://rickandmortyapi.com/api/character/398",
            "https://rickandmortyapi.com/api/character/405"
            ],
            "url": "https://rickandmortyapi.com/api/episode/2",
            "created": "2017-11-10T12:56:33.916Z"
        },
        {
            "id": 3,
            "name": "Anatomy Park",
            "air_date": "December 16, 2013",
            "episode": "S01E03",
            "characters": [
            "https://rickandmortyapi.com/api/character/1",
            "https://rickandmortyapi.com/api/character/2",
            "https://rickandmortyapi.com/api/character/12",
            "https://rickandmortyapi.com/api/character/17",
            "https://rickandmortyapi.com/api/character/38",
            "https://rickandmortyapi.com/api/character/100"
            ],
            "url": "https://rickandmortyapi.com/api/episode/3",
            "created": "2017-11-10T12:56:34.022Z"
        }
        ]
    }
""".trimIndent()

    val characters = """
        
        {
            "info": {
                "count": 826,
                "pages": 42,
                "next": "https://rickandmortyapi.com/api/character/?page=2",
                "prev": null
            },
            "results": [
                {
                    "id": 1,
                    "name": "Rick Sanchez",
                    "status": "Alive",
                    "species": "Human",
                    "type": "",
                    "gender": "Male",
                    "origin": {
                        "name": "Earth (C-137)",
                        "url": "https://rickandmortyapi.com/api/location/1"
                    },
                    "location": {
                        "name": "Citadel of Ricks",
                        "url": "https://rickandmortyapi.com/api/location/3"
                    },
                    "image": "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                    "episode": [
                        "https://rickandmortyapi.com/api/episode/1",
                        "https://rickandmortyapi.com/api/episode/2",
                        "https://rickandmortyapi.com/api/episode/3",
                        "https://rickandmortyapi.com/api/episode/49",
                        "https://rickandmortyapi.com/api/episode/50",
                        "https://rickandmortyapi.com/api/episode/51"
                    ],
                    "url": "https://rickandmortyapi.com/api/character/1",
                    "created": "2017-11-04T18:48:46.250Z"
                },
                {
                    "id": 2,
                    "name": "Morty Smith",
                    "status": "Alive",
                    "species": "Human",
                    "type": "",
                    "gender": "Male",
                    "origin": {
                        "name": "unknown",
                        "url": ""
                    },
                    "location": {
                        "name": "Citadel of Ricks",
                        "url": "https://rickandmortyapi.com/api/location/3"
                    },
                    "image": "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
                    "episode": [
                        "https://rickandmortyapi.com/api/episode/1",
                        "https://rickandmortyapi.com/api/episode/2",
                        "https://rickandmortyapi.com/api/episode/3",
                        "https://rickandmortyapi.com/api/episode/4",
                        "https://rickandmortyapi.com/api/episode/5",
                        "https://rickandmortyapi.com/api/episode/6",
                        "https://rickandmortyapi.com/api/episode/7",
                        "https://rickandmortyapi.com/api/episode/51"
                    ],
                    "url": "https://rickandmortyapi.com/api/character/2",
                    "created": "2017-11-04T18:50:21.651Z"
                },
                {
                    "id": 3,
                    "name": "Summer Smith",
                    "status": "Alive",
                    "species": "Human",
                    "type": "",
                    "gender": "Female",
                    "origin": {
                        "name": "Earth (Replacement Dimension)",
                        "url": "https://rickandmortyapi.com/api/location/20"
                    },
                    "location": {
                        "name": "Earth (Replacement Dimension)",
                        "url": "https://rickandmortyapi.com/api/location/20"
                    },
                    "image": "https://rickandmortyapi.com/api/character/avatar/3.jpeg",
                    "episode": [
                        "https://rickandmortyapi.com/api/episode/6",
                        "https://rickandmortyapi.com/api/episode/7",
                        "https://rickandmortyapi.com/api/episode/8",
                        "https://rickandmortyapi.com/api/episode/48",
                        "https://rickandmortyapi.com/api/episode/49",
                        "https://rickandmortyapi.com/api/episode/51"
                    ],
                    "url": "https://rickandmortyapi.com/api/character/3",
                    "created": "2017-11-04T19:09:56.428Z"
                },
                {
                    "id": 4,
                    "name": "Beth Smith",
                    "status": "Alive",
                    "species": "Human",
                    "type": "",
                    "gender": "Female",
                    "origin": {
                        "name": "Earth (Replacement Dimension)",
                        "url": "https://rickandmortyapi.com/api/location/20"
                    },
                    "location": {
                        "name": "Earth (Replacement Dimension)",
                        "url": "https://rickandmortyapi.com/api/location/20"
                    },
                    "image": "https://rickandmortyapi.com/api/character/avatar/4.jpeg",
                    "episode": [
                        "https://rickandmortyapi.com/api/episode/6",
                        "https://rickandmortyapi.com/api/episode/7",
                        "https://rickandmortyapi.com/api/episode/8",
                        "https://rickandmortyapi.com/api/episode/51"
                    ],
                    "url": "https://rickandmortyapi.com/api/character/4",
                    "created": "2017-11-04T19:22:43.665Z"
                },
                {
                    "id": 5,
                    "name": "Jerry Smith",
                    "status": "Alive",
                    "species": "Human",
                    "type": "",
                    "gender": "Male",
                    "origin": {
                        "name": "Earth (Replacement Dimension)",
                        "url": "https://rickandmortyapi.com/api/location/20"
                    },
                    "location": {
                        "name": "Earth (Replacement Dimension)",
                        "url": "https://rickandmortyapi.com/api/location/20"
                    },
                    "image": "https://rickandmortyapi.com/api/character/avatar/5.jpeg",
                    "episode": [
                        "https://rickandmortyapi.com/api/episode/6",
                        "https://rickandmortyapi.com/api/episode/7",
                        "https://rickandmortyapi.com/api/episode/8",
                        "https://rickandmortyapi.com/api/episode/9",
                        "https://rickandmortyapi.com/api/episode/10",
                        "https://rickandmortyapi.com/api/episode/51"
                    ],
                    "url": "https://rickandmortyapi.com/api/character/5",
                    "created": "2017-11-04T19:26:56.301Z"
                }
            ]
        }
""".trimIndent()

    val locations = """
        {
            "info": {
                "count": 126,
                "pages": 7,
                "next": "https://rickandmortyapi.com/api/location?page=2",
                "prev": null
            },
            "results": [
                {
                    "id": 1,
                    "name": "Earth (C-137)",
                    "type": "Planet",
                    "dimension": "Dimension C-137",
                    "residents": [
                        "https://rickandmortyapi.com/api/character/38",
                        "https://rickandmortyapi.com/api/character/45",
                        "https://rickandmortyapi.com/api/character/71",
                        "https://rickandmortyapi.com/api/character/82",
                        "https://rickandmortyapi.com/api/character/394"
                    ],
                    "url": "https://rickandmortyapi.com/api/location/1",
                    "created": "2017-11-10T12:42:04.162Z"
                },
                {
                    "id": 2,
                    "name": "Abadango",
                    "type": "Cluster",
                    "dimension": "unknown",
                    "residents": [
                        "https://rickandmortyapi.com/api/character/6"
                    ],
                    "url": "https://rickandmortyapi.com/api/location/2",
                    "created": "2017-11-10T13:06:38.182Z"
                },
                {
                    "id": 3,
                    "name": "Citadel of Ricks",
                    "type": "Space station",
                    "dimension": "unknown",
                    "residents": [
                        "https://rickandmortyapi.com/api/character/8",
                        "https://rickandmortyapi.com/api/character/14",
                        "https://rickandmortyapi.com/api/character/15",
                        "https://rickandmortyapi.com/api/character/819",
                        "https://rickandmortyapi.com/api/character/820",
                        "https://rickandmortyapi.com/api/character/818"
                    ],
                    "url": "https://rickandmortyapi.com/api/location/3",
                    "created": "2017-11-10T13:08:13.191Z"
                },
                {
                    "id": 4,
                    "name": "Worldender's lair",
                    "type": "Planet",
                    "dimension": "unknown",
                    "residents": [
                        "https://rickandmortyapi.com/api/character/10",
                        "https://rickandmortyapi.com/api/character/81",
                        "https://rickandmortyapi.com/api/character/208",
                        "https://rickandmortyapi.com/api/character/226",
                        "https://rickandmortyapi.com/api/character/395"
                    ],
                    "url": "https://rickandmortyapi.com/api/location/4",
                    "created": "2017-11-10T13:08:20.569Z"
                },
                {
                    "id": 5,
                    "name": "Anatomy Park",
                    "type": "Microverse",
                    "dimension": "Dimension C-137",
                    "residents": [
                        "https://rickandmortyapi.com/api/character/12",
                        "https://rickandmortyapi.com/api/character/17",
                        "https://rickandmortyapi.com/api/character/96",
                        "https://rickandmortyapi.com/api/character/300"
                    ],
                    "url": "https://rickandmortyapi.com/api/location/5",
                    "created": "2017-11-10T13:08:46.060Z"
                }
            ]
        }
""".trimIndent()

}
