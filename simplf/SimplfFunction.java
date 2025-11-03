package simplf;
 
import java.util.List;

class SimplfFunction implements SimplfCallable {

    private final Stmt.Function declaration;
    private Environment closure;

    SimplfFunction(Stmt.Function declaration, Environment closure) {
        this.declaration = declaration;
        this.closure = closure;
    }

    public void setClosure(Environment environment) {
        this.closure = environment;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> args) {
        Environment env = new Environment(closure);
        // bind parameters
        for (int i = 0; i < declaration.params.size(); i++) {
            Token t = declaration.params.get(i);
            Object val = null;
            if (i < args.size())
                val = args.get(i);
            env.define(t, t.lexeme, val);
        }

        // execute body in new environment
        return interpreter.executeBlock(declaration.body, env);
    }

    @Override
    public String toString() {
        if (declaration != null && declaration.name != null)
            return "<fn " + declaration.name.lexeme + ">";
        return "<fn >";
    }

}