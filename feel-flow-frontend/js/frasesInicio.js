const phrases = [
    "La vida es lo que pasa mientras estás ocupado haciendo otros planes.",
    "El único modo de hacer un gran trabajo es amar lo que haces.",
    "La creatividad es la inteligencia divirtiéndose.",
    "El fracaso es simplemente la oportunidad de comenzar de nuevo, esta vez de manera más inteligente.",
    "La mejor manera de predecir el futuro es crearlo.",
    "Siempre parece imposible hasta que se hace.",
    "Trabajo deprisa para vivir despacio."
];

function getRandomPhrase() {
    const randomIndex = Math.floor(Math.random() * phrases.length);
    return phrases[randomIndex];
}

const randomPhraseElement = document.getElementById("frase");
randomPhraseElement.textContent = getRandomPhrase();