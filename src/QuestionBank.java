
class QuestionBank {

    public static String[][] getQuestions(String language, int level) {
        switch (language.toLowerCase()) {
            case "spanish":
                return getSpanishQuestions(level);
            case "tamil":
                return getTamilQuestions(level);
            case "german":
                return getGermanQuestions(level);
            case "french":
                return getFrenchQuestions(level);
            default:
                return new String[][]{
                        {"Sample Question?", "Option A", "Option B", "Option C", "1"}
                };
        }
    }

    // Sample questions with increasing difficulty for each language
    private static String[][] getSpanishQuestions(int level) {
        switch (level) {
            case 1:
                return new String[][]{
                        {"¿Qué significa 'hola'?", "Hola", "Adiós", "Por favor", "1"},
                        {"¿Qué es 'gracias' en inglés?", "Por favor", "Gracias", "Sí", "2"},
                        {"¿Qué significa 'amigo'?", "Perro", "Amigo", "Libro", "2"},
                        {"¿Qué es 'comida' en inglés?", "Comida", "Agua", "Coche", "1"},
                        {"¿Cómo se dice 'thank you' en español?", "Gracias", "Bienvenido", "Por favor", "1"}
                };
            case 2:
                return new String[][]{
                        {"¿Qué significa 'libro'?", "Libro", "Silla", "Mesa", "1"},
                        {"¿Cómo se dice 'cat' en español?", "Gato", "Perro", "Conejo", "1"},
                        {"¿Qué significa 'rojo'?", "Rojo", "Verde", "Azul", "1"},
                        {"¿Cómo se dice 'happy' en español?", "Feliz", "Triste", "Enfermo", "1"},
                        {"¿Qué significa 'mañana'?", "Hoy", "Mañana", "Noche", "2"}
                };
            case 3:
                return new String[][]{
                        {"¿Qué es un 'banco' en inglés?", "Bank", "Table", "Chair", "1"},
                        {"¿Cómo se dice 'child' en español?", "Niño", "Mujer", "Hombre", "1"},
                        {"¿Qué significa 'feliz'?", "Sad", "Happy", "Tired", "2"},
                        {"¿Qué es 'cielo' en inglés?", "Sky", "Cloud", "Sun", "1"},
                        {"¿Cómo se dice 'food' en español?", "Comida", "Bebida", "Dinero", "1"}
                };
            case 4:
                return new String[][]{
                        {"¿Qué significa 'mañana' en inglés?", "Morning", "Night", "Tomorrow", "3"},
                        {"¿Cómo se dice 'tree' en español?", "Árbol", "Flor", "Hoja", "1"},
                        {"¿Qué significa 'rojo' en inglés?", "Red", "Green", "Blue", "1"},
                        {"¿Cómo se dice 'cat' en español?", "Perro", "Gato", "Pájaro", "2"},
                        {"¿Qué significa 'feliz'?", "Sad", "Happy", "Angry", "2"}
                };
            case 5:
                return new String[][]{
                        {"¿Qué es 'feliz' en inglés?", "Sad", "Happy", "Angry", "2"},
                        {"¿Qué significa 'rojo' en inglés?", "Red", "Green", "Blue", "1"},
                        {"¿Cómo se dice 'tree' en español?", "Árbol", "Flor", "Hoja", "1"},
                        {"¿Qué es un 'banco' en inglés?", "Bank", "Table", "Chair", "1"},
                        {"¿Qué es 'cielo' en inglés?", "Sky", "Cloud", "Sun", "1"}
                };
            default:
                return new String[][]{
                        {"¿Qué significa 'hola'?", "Hola", "Adiós", "Por favor", "1"}
                };
        }
    }

    private static String[][] getTamilQuestions(int level) {
        switch (level) {
            case 1:
                return new String[][]{
                        {"What is 'Vanakkam'?", "Vanakkam", "Poy", "Please", "1"},
                        {"What is 'Nandri' in English?", "Please", "Thank you", "Yes", "2"},
                        {"What is the meaning of 'Nanban'?", "Cow", "Friend", "Book", "2"},
                        {"What is 'Unavu' in English?", "Food", "Water", "Car", "1"},
                        {"How do you say 'thank you' in Tamil?", "Nandri", "Welcome", "Please", "1"}
                };
            case 2:
                return new String[][]{
                        {"What is 'Pusthagam'?", "Book", "Chair", "Table", "1"},
                        {"How do you say 'cat' in Tamil?", "Poonai", "Naai", "Maan", "1"},
                        {"What is the meaning of 'Sivappu'?", "Red", "Green", "Blue", "1"},
                        {"How do you say 'happy' in Tamil?", "Santhosham", "Kashtam", "Noi", "1"},
                        {"What does 'Nalai' mean?", "Today", "Tomorrow", "Night", "2"}
                };
            case 3:
                return new String[][]{
                        {"What is 'Thaai'?", "Mother", "Father", "Brother", "1"},
                        {"How do you say 'to eat' in Tamil?", "Saapidu", "Kudi", "Thoongu", "1"},
                        {"What is 'Pasanga'?", "Men", "Children", "Dogs", "2"},
                        {"How do you say 'to go' in Tamil?", "Poga", "Paar", "Irundhaal", "1"},
                        {"What is the meaning of 'Veedu'?", "Car", "House", "Tree", "2"}
                };
            case 4:
                return new String[][]{
                        {"How do you say 'I eat' in Tamil?", "Naan saapiduven", "Saapida", "Saapitten", "1"},
                        {"What does 'Kadhavai thira' mean?", "Close the door", "Open the door", "Shut the door", "2"},
                        {"What is 'Naangal'?", "They", "We", "You", "2"},
                        {"How do you say 'he drinks' in Tamil?", "Avan kudikkiraan", "Avargal kudikkiraargal", "Nee kudikkiraai", "1"},
                        {"What does 'Avargal padikkiraargal' mean?", "They read", "He reads", "We write", "1"}
                };
            case 5:
                return new String[][]{
                        {"What is 'Aanalum'?", "Because", "Although", "While", "2"},
                        {"What does 'Vegama' mean?", "Quickly", "Slowly", "Softly", "1"},
                        {"What is 'Aanal'?", "However", "Therefore", "Also", "1"},
                        {"What does 'Pinbu' mean?", "Before", "After", "Now", "2"},
                        {"What is 'Pothu'?", "Since", "During", "While", "3"}
                };
            default:
                return new String[][]{
                        {"What is 'Vanakkam'?", "Vanakkam", "Poy", "Please", "1"}
                };
        }
    }


    private static String[][] getGermanQuestions(int level) {
        switch (level) {
            case 1:
                return new String[][]{
                        {"Was bedeutet 'Hallo'?", "Hallo", "Tschüss", "Bitte", "1"},
                        {"Was ist 'Danke' auf Englisch?", "Please", "Thank you", "Yes", "2"},
                        {"Was bedeutet 'Freund'?", "Hund", "Freund", "Buch", "2"},
                        {"Was ist 'Essen' auf Englisch?", "Food", "Water", "Car", "1"},
                        {"Wie sagt man 'thank you' auf Deutsch?", "Danke", "Willkommen", "Bitte", "1"}
                };
            case 2:
                return new String[][]{
                        {"Was bedeutet 'Buch'?", "Book", "Chair", "Table", "1"},
                        {"Wie sagt man 'cat' auf Deutsch?", "Katze", "Hund", "Hase", "1"},
                        {"Was bedeutet 'rot'?", "Red", "Green", "Blue", "1"},
                        {"Wie sagt man 'happy' auf Deutsch?", "Glücklich", "Traurig", "Krank", "1"},
                        {"Was bedeutet 'morgen'?", "Today", "Tomorrow", "Night", "2"}
                };
            case 3:
                return new String[][]{
                        {"Was ist eine 'Bank' auf Englisch?", "Bank", "Table", "Chair", "1"},
                        {"Wie sagt man 'child' auf Deutsch?", "Kind", "Frau", "Mann", "1"},
                        {"Was bedeutet 'glücklich'?", "Sad", "Happy", "Tired", "2"},
                        {"Was ist 'Himmel' auf Englisch?", "Sky", "Cloud", "Sun", "1"},
                        {"Wie sagt man 'food' auf Deutsch?", "Essen", "Trinken", "Geld", "1"}
                };
            case 4:
                return new String[][]{
                        {"Was bedeutet 'morgen' auf Englisch?", "Morning", "Night", "Tomorrow", "3"},
                        {"Wie sagt man 'tree' auf Deutsch?", "Baum", "Blume", "Blatt", "1"},
                        {"Was bedeutet 'rot' auf Englisch?", "Red", "Green", "Blue", "1"},
                        {"Wie sagt man 'cat' auf Deutsch?", "Hund", "Katze", "Vogel", "2"},
                        {"Was bedeutet 'glücklich'?", "Sad", "Happy", "Angry", "2"}
                };
            case 5:
                return new String[][]{
                        {"Was ist 'glücklich' auf Englisch?", "Sad", "Happy", "Angry", "2"},
                        {"Was bedeutet 'rot' auf Englisch?", "Red", "Green", "Blue", "1"},
                        {"Wie sagt man 'tree' auf Deutsch?", "Baum", "Blume", "Blatt", "1"},
                        {"Was ist eine 'Bank' auf Englisch?", "Bank", "Table", "Chair", "1"},
                        {"Was ist 'Himmel' auf Englisch?", "Sky", "Cloud", "Sun", "1"}
                };
            default:
                return new String[][]{
                        {"Was bedeutet 'Hallo'?", "Hallo", "Tschüss", "Bitte", "1"}
                };
        }
    }


    private static String[][] getFrenchQuestions(int level) {
        switch (level) {
            case 1:
                return new String[][]{
                        {"Que signifie 'bonjour'?", "Bonjour", "Au revoir", "S'il vous plaît", "1"},
                        {"Qu'est-ce que 'merci' en anglais?", "Please", "Thank you", "Yes", "2"},
                        {"Que signifie 'ami'?", "Chien", "Ami", "Livre", "2"},
                        {"Que signifie 'nourriture'?", "Food", "Water", "Car", "1"},
                        {"Comment dit-on 'thank you' en français?", "Merci", "Bienvenue", "S'il vous plaît", "1"}
                };
            case 2:
                return new String[][]{
                        {"Que signifie 'livre'?", "Book", "Chair", "Table", "1"},
                        {"Comment dit-on 'cat' en français?", "Chat", "Chien", "Lapin", "1"},
                        {"Que signifie 'rouge'?", "Red", "Green", "Blue", "1"},
                        {"Comment dit-on 'happy' en français?", "Heureux", "Triste", "Malade", "1"},
                        {"Que signifie 'demain'?", "Today", "Tomorrow", "Night", "2"}
                };
            case 3:
                return new String[][]{
                        {"Qu'est-ce qu'une 'banque' en anglais?", "Bank", "Table", "Chair", "1"},
                        {"Comment dit-on 'child' en français?", "Enfant", "Femme", "Homme", "1"},
                        {"Que signifie 'heureux'?", "Sad", "Happy", "Tired", "2"},
                        {"Que signifie 'ciel'?", "Sky", "Cloud", "Sun", "1"},
                        {"Comment dit-on 'food' en français?", "Nourriture", "Boisson", "Argent", "1"}
                };
            case 4:
                return new String[][]{
                        {"Que signifie 'demain' en anglais?", "Morning", "Night", "Tomorrow", "3"},
                        {"Comment dit-on 'tree' en français?", "Arbre", "Fleur", "Feuille", "1"},
                        {"Que signifie 'rouge' en anglais?", "Red", "Green", "Blue", "1"},
                        {"Comment dit-on 'cat' en français?", "Chien", "Chat", "Oiseau", "2"},
                        {"Que signifie 'heureux'?", "Sad", "Happy", "Angry", "2"}
                };
            case 5:
                return new String[][]{
                        {"Que signifie 'heureux' en anglais?", "Sad", "Happy", "Angry", "2"},
                        {"Que signifie 'rouge' en anglais?", "Red", "Green", "Blue", "1"},
                        {"Comment dit-on 'tree' en français?", "Arbre", "Fleur", "Feuille", "1"},
                        {"Qu'est-ce qu'une 'banque' en anglais?", "Bank", "Table", "Chair", "1"},
                        {"Que signifie 'ciel' en anglais?", "Sky", "Cloud", "Sun", "1"}
                };
            default:
                return new String[][]{
                        {"Que signifie 'bonjour'?", "Bonjour", "Au revoir", "S'il vous plaît", "1"}
                };
        }
    }
}

