package simplf; 

class Environment {
    private AssocList values = null;
    private final Environment enclosing;

    Environment() {
        this.enclosing = null;
    }

    Environment(Environment enclosing) {
        this.enclosing = enclosing;
    }

    void define(Token varToken, String name, Object value) {
        values = new AssocList(name, value, values);
    }

    void assign(Token name, Object value) {
        for (AssocList a = values; a != null; a = a.next) {
            if (a.name.equals(name.lexeme)) {
                a.value = value;
                return;
            }
        }
        if (enclosing != null) {
            enclosing.assign(name, value);
            return;
        }
        throw new RuntimeError(name, "Undefined variable '" + name.lexeme + "'.");
    }

    Object get(Token name) {
        for (AssocList a = values; a != null; a = a.next) {
            if (a.name.equals(name.lexeme)) {
                return a.value;
            }
        }
        if (enclosing != null) {
            return enclosing.get(name);
        }
        throw new RuntimeError(name, "Undefined variable '" + name.lexeme + "'.");
    }
}

