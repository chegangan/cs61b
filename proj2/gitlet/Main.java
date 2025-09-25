package gitlet;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author chegan
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        if(args.length == 0) {
            System.out.println("Please enter a command.");
            System.exit(0);
        }
        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                Repository.init();
                break;
            case "add":
                Repository.add(args[1]);
                break;
            case "commit":
                Repository.commit(args[1]);
                break;
            case "log":
                Repository.log();
                break;
            case "checkout":
                if(args.length == 2) {
                    Repository.checkout(args[1]);
                } else if(args.length == 3) {
                    Repository.checkout(args[1], args[2]);
                } else if(args.length == 4) {
                    Repository.checkout(args[1], args[2], args[3]);
                } else {
                    System.out.println("No command with that name exists.");
                    System.exit(0);
                }
                break;
            case "status":
                Repository.status();
                break;
            case "rm":
                Repository.rm(args[1]);
                break;
            case "branch":
                Repository.branch(args[1]);
                break;
            case "rm-branch":
                Repository.rmBranch(args[1]);
                break;
            case "reset":
                Repository.reset(args[1]);
                break;
            case "merge":
                Repository.merge(args[1]);
                break;
            default:
                System.out.println("No command with that name exists.");
                System.exit(0);
        }
    }
}
