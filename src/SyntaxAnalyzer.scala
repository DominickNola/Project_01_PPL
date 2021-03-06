/*
 * CS3210 - Principles of Programming Languages - Fall 2020
 * Instructor: Thyago Mota
 * Description: Activity 07 - Syntax Analyzer
 */

/*
'' denotes terminal symbol, [] optional, {} repetition
program =  ́program ́ identifier body  ́. ́
identifier = letter { ( letter | digit ) }
body = [ var_sct ] block
var_sct =  ́var ́ var_dcl {  ́; ́ var_dcl }
var_dcl = identifier { identifier }  ́: ́ type
type =  ́Integer ́ |  ́Boolean ́
block =  ́begin ́ stmt {  ́; ́ stmt } end
stmt = assgm_stmt | read_stmt | write_stmt | if_stmt | while_stmt | block
assgm_stmt = identifier  ́:= ́ expr
read_stmt =  ́read ́ identifier
write_stmt =  ́write ́ ( identifier | literal )
if_stmt =  ́if ́ bool_expr  ́then ́ stmt [  ́else ́ stmt ]
while_stmt =  ́while ́ bool_expr  ́do ́ stmt
expr = arithm_expr | bool_expr
arithm_expr = arithm_expr (  ́+ ́ |  ́- ́ ) term | term
term = term  ́* ́ factor | factor
factor = identifier | int_literal
literal = int_literal | bool_literal
int_literal = digit { digit }
bool_litreal =  ́true ́ |  ́false ́
bool_expr = bool_literal | arithm_expr (  ́> ́ |  ́>= ́ |  ́= ́ |  ́<= ́ |  ́< ́ ) arithm_expr
letter =  ́a ́ |  ́b ́ |  ́c ́ |  ́d ́ |  ́e ́ |  ́f ́ |  ́g ́ |  ́h ́ |  ́i ́ |  ́j ́ |  ́k ́ |  ́l ́ |  ́m ́ |
 ́n ́ |  ́o ́ |  ́p ́ |  ́q ́ |  ́r ́ |  ́s ́ |  ́t ́ |  ́u ́ |  ́v ́ |  ́w ́ |  ́x ́ |  ́y ́ |  ́z ́
digit =  ́0 ́ |  ́1 ́ |  ́2 ́ |  ́3 ́ |  ́4 ́ |  ́5 ́ |  ́6 ́ |  ́7 ́ |  ́8 ́ |  ́9 ́
 */

class SyntaxAnalyzer(private var source: String) {

  private var it = new LexicalAnalyzer(source).iterator
  private var lexemeUnit: LexemeUnit = null
  var switch = 0


  private def getLexemeUnit() = {
    if (lexemeUnit == null)
      lexemeUnit = it.next()
  }

  def parse(): Tree = {
    parseProgram()
  }

  // TODO: finish the syntax analyzer

  // program =  ́program ́ identifier body  ́. ́
  private def parseProgram() = {
    // create a tree with label "program"
    val tree = new Tree("program")

    // TODOd: call getLexemeUnit
    getLexemeUnit()

    // TODOd: if token is NOT EOF, add result of "parseTerm" and "parseExpressionPrime" as new branches
    if (lexemeUnit.getToken() != Token.EOF) {
      if(lexemeUnit.getToken() == Token.PROGRAM) {
        tree.add(new Tree(lexemeUnit.getLexeme()))
        lexemeUnit = null
      }
      getLexemeUnit()
      if(lexemeUnit.getToken() == Token.IDENTIFIER) {
        tree.add(parseIdentifier())
        lexemeUnit = null
      } else
        throw new Exception("Syntax Analyzer Error: \"identifier\" was expected!")

      tree.add(parseBody())

      // closing period
      getLexemeUnit()
      tree.add(new Tree(lexemeUnit.getLexeme()))
      lexemeUnit = null

    }
    else
      throw new Exception("Syntax Analyzer Error: \"program\" was expected!")

    // return the tree
    tree
  }

  // identifier = letter { ( letter | digit ) }
  // TODOd: return a new tree with the label "identifier" followed by the actual lexeme
  private def parseIdentifier() = new Tree(lexemeUnit.toString)

  // body = [ var_sct ] block
  private def parseBody() = {
    val tree = new Tree("body")

    getLexemeUnit()
    if (lexemeUnit.getToken() != Token.EOF) {
      if(lexemeUnit.getToken() == Token.VAR) {
        tree.add(parseVarsct())
      }

      tree.add(parseBlock())
      getLexemeUnit()
      lexemeUnit = null
    }
    else
      throw new Exception("Syntax Analyzer Error: \"body\" was expected!")

    // TODOd: return the tree
    tree
  }

  // block =  ́begin ́ stmt {  ́; ́ stmt } end
  private def parseBlock(): Tree = {
    val tree = new Tree("block")
    getLexemeUnit()

    if (lexemeUnit.getToken() != Token.EOF) {
      if(lexemeUnit.getToken() == Token.BEGIN) {
        tree.add(new Tree(lexemeUnit.getLexeme()))
        lexemeUnit = null
      }


      tree.add(parseStatement())
      getLexemeUnit()
      if(lexemeUnit.getToken() == Token.SEMICOLON) {
        tree.add(parsePunctuator())
      }

      tree.add(parseStatement())
      getLexemeUnit()
      if(lexemeUnit.getToken() == Token.SEMICOLON) {
        tree.add(parsePunctuator())
      }

      tree.add(parseStatement())
      getLexemeUnit()
      if(lexemeUnit.getToken() == Token.SEMICOLON) {
        tree.add(parsePunctuator())
      }

      tree.add(parseStatement())
      getLexemeUnit()
      if(lexemeUnit.getToken() == Token.END) {
        tree.add(new Tree(lexemeUnit.getLexeme()))

      }
      else
        throw new Exception("Syntax Analyzer Error: \"begin\" was expected!")


    }

    // TODOd: return the tree
    tree
  }

  private def parsePunctuator() = {
    getLexemeUnit()
    val tree = new Tree(lexemeUnit.getLexeme())
    if (lexemeUnit.getToken() != Token.EOF) {

    }
    else
      throw new Exception("Syntax Analyzer Error: factor was expected!")
    // TODOd: return the tree
    tree
  }

  // term = factor term'
  private def parseBegin() = {
    // TODOd: create a tree with label "term"
    val tree = new Tree("begin")

    // TODOd: call getLexemeUnit
    getLexemeUnit()

    // TODOd: if token is NOT EOF, add result of "parseFactor" and "parseTermPrime" as new branches
    if (lexemeUnit.getToken() != Token.EOF) {

    }
    // TODOd: otherwise, throw an exception saying that "factor" was expected
    else
      throw new Exception("Syntax Analyzer Error: punctuator was expected!")

    // TODOd: return the tree
    tree
  }

  // term = factor term'
  private def parseStatement() = {
    // TODOd: create a tree with label "term"
    val tree = new Tree("stmt")
    lexemeUnit = null

    // TODOd: call getLexemeUnit
    getLexemeUnit()

    // TODOd: if token is NOT EOF, add result of "parseFactor" and "parseTermPrime" as new branches
    if (lexemeUnit.getToken() != Token.EOF) {
      if (lexemeUnit.getToken() == Token.READ) {
        tree.add(parseRead())
        lexemeUnit = null
        switch = 1
      } else if (lexemeUnit.getToken() == Token.IDENTIFIER && switch == 1) {
        tree.add(parseDefined())
      } else if (lexemeUnit.getToken() == Token.WRITE) {
        tree.add(parseWrite())
      } else
        throw new Exception("Syntax Analyzer Error: assignment was expected!")
    }
    // TODOd: otherwise, throw an exception saying that "factor" was expected
    else
      throw new Exception("Syntax Analyzer Error: stmt was expected!")

    tree
  }

  // var_sct =  ́var ́ var_dcl {  ́; ́ var_dcl }
  private def parseWrite() = {
    // TODOd: create a tree with label "expression"
    val tree = new Tree("write_stmt")

    // TODOd: call getLexemeUnit
    getLexemeUnit()

    // TODOd: if token is NOT EOF, add result of "parseTerm" and "parseExpressionPrime" as new branches
    if (lexemeUnit.getToken() != Token.EOF) {
      //      lexemeUnit = null
      //      getLexemeUnit()
      tree.add(new Tree(lexemeUnit.getLexeme()))
      lexemeUnit = null
        getLexemeUnit()
        tree.add(new Tree(lexemeUnit.toString()))
        lexemeUnit = null

    }
    else
      throw new Exception("Syntax Analyzer Error: \"write_stmt\" was expected!")

    // TODOd: return the tree
    tree
  }

  // assgm_stmt = identifier  ́:= ́ expr
  private def parseDefined() = {
    // TODOd: create a tree with label "expression"
    val tree = new Tree("assgm_stmt")

    // TODOd: call getLexemeUnit
    getLexemeUnit()

    // TODOd: if token is NOT EOF, add result of "parseTerm" and "parseExpressionPrime" as new branches
    if (lexemeUnit.getToken() != Token.EOF) {
      //      lexemeUnit = null
      //      getLexemeUnit()
      if (lexemeUnit.getToken() == Token.IDENTIFIER) {
        tree.add(new Tree(lexemeUnit.toString()))
        lexemeUnit = null
      }
      getLexemeUnit()
       if (lexemeUnit.getToken() == Token.DEFINED) {
         tree.add(new Tree(lexemeUnit.toString()))
         lexemeUnit = null
         tree.add(parseExpression())
       }



    }
    else
      throw new Exception("Syntax Analyzer Error: \"assgm_stmt\" was expected!")

    // TODOd: return the tree
    tree
  }

  // expr = arithm_expr | bool_expr
  private def parseExpression() = {
    // TODOd: create a tree with label "expression"
    val tree = new Tree("expr")

    // TODOd: call getLexemeUnit
    getLexemeUnit()

    // TODOd: if token is NOT EOF, add result of "parseTerm" and "parseExpressionPrime" as new branches
    if (lexemeUnit.getToken() != Token.EOF) {
      if (lexemeUnit.getToken() == Token.IDENTIFIER) {
        tree.add(parseArith())
      }
      //      lexemeUnit = null
      //      getLexemeUnit()

//      getLexemeUnit()
//      tree.add(new Tree(lexemeUnit.toString()))
//      lexemeUnit = null

    }
    else
      throw new Exception("Syntax Analyzer Error: \"expr\" was expected!")

    // TODOd: return the tree
    tree
  }

  // var_sct =  ́var ́ var_dcl {  ́; ́ var_dcl }
  private def parseArith() = {
    // TODOd: create a tree with label "expression"
    val tree = new Tree("arith_expr")

    // TODOd: call getLexemeUnit
    getLexemeUnit()

    // TODOd: if token is NOT EOF, add result of "parseTerm" and "parseExpressionPrime" as new branches
    if (lexemeUnit.getToken() != Token.EOF) {
      tree.add(parseTerm())
      tree.add(parseArithExpPrime())
      //      lexemeUnit = null
      //      getLexemeUnit()
//      tree.add(new Tree(lexemeUnit.toString()))
//      lexemeUnit = null
//      getLexemeUnit()
//      tree.add(new Tree(lexemeUnit.toString()))
//      lexemeUnit = null

    }
    else
      throw new Exception("Syntax Analyzer Error: \"arith_expr\" was expected!")

    // TODOd: return the tree
    tree
  }

  // term = term  ́* ́ factor | factor
  private def parseArithExpPrime() = {
    val tree = new Tree("arith_expr'")

    getLexemeUnit()

    if (lexemeUnit.getToken() != Token.EOF) {
        tree.add(new Tree(lexemeUnit.getLexeme()))
        lexemeUnit = null
        tree.add(parseTerm())
    }
    else
      throw new Exception("Syntax Analyzer Error: \"arith_expr'\" was expected!")

    tree
  }

  // term = term  ́* ́ factor | factor
  private def parseTerm() = {
    // TODOd: create a tree with label "expression"
    val tree = new Tree("term")

    // TODOd: call getLexemeUnit
    getLexemeUnit()

    // TODOd: if token is NOT EOF, add result of "parseTerm" and "parseExpressionPrime" as new branches
    if (lexemeUnit.getToken() != Token.EOF) {
      tree.add(parseFactor())
      tree.add(parseTermPrime())

      //      lexemeUnit = null
      //      getLexemeUnit()
//      tree.add(new Tree(lexemeUnit.toString()))
//      lexemeUnit = null
//      getLexemeUnit()
//      tree.add(new Tree(lexemeUnit.toString()))
//      lexemeUnit = null

    }
    else
      throw new Exception("Syntax Analyzer Error: \"term\" was expected!")

    // TODOd: return the tree
    tree
  }

  // term = term  ́* ́ factor | factor
  private def parseTermPrime() = {
    // TODOd: create a tree with label "expression"
    val tree = new Tree("term'")

    // TODOd: call getLexemeUnit
    getLexemeUnit()

    // TODOd: if token is NOT EOF, add result of "parseTerm" and "parseExpressionPrime" as new branches
    if (lexemeUnit.getToken() != Token.EOF) {

    }
    else
      throw new Exception("Syntax Analyzer Error: \"term'\" was expected!")

    // TODOd: return the tree
    tree
  }

  // term = term  ́* ́ factor | factor
  private def parseFactor() = {
    // TODOd: create a tree with label "expression"
    val tree = new Tree("factor")

    // TODOd: call getLexemeUnit
    getLexemeUnit()

    // TODOd: if token is NOT EOF, add result of "parseTerm" and "parseExpressionPrime" as new branches
    if (lexemeUnit.getToken() != Token.EOF) {
      tree.add(new Tree(lexemeUnit.toString()))
      lexemeUnit = null
    }
    else
      throw new Exception("Syntax Analyzer Error: \"factor\" was expected!")

    // TODOd: return the tree
    tree
  }

  // var_sct =  ́var ́ var_dcl {  ́; ́ var_dcl }
  private def parseRead() = {
    // TODOd: create a tree with label "expression"
    val tree = new Tree("read_stmt")

    // TODOd: call getLexemeUnit
    getLexemeUnit()

    // TODOd: if token is NOT EOF, add result of "parseTerm" and "parseExpressionPrime" as new branches
    if (lexemeUnit.getToken() != Token.EOF) {
//      lexemeUnit = null
//      getLexemeUnit()
      tree.add(new Tree(lexemeUnit.toString()))
      lexemeUnit = null
      getLexemeUnit()
      tree.add(new Tree(lexemeUnit.toString()))
      lexemeUnit = null

    }
    else
      throw new Exception("Syntax Analyzer Error: \"read_stmt\" was expected!")

    // TODOd: return the tree
    tree
  }

  // var_sct =  ́var ́ var_dcl {  ́; ́ var_dcl }
  private def parseVarsct() = {
    // TODOd: create a tree with label "expression"
    val tree = new Tree("var_sct")

    if (lexemeUnit.getToken() != Token.EOF) {
      tree.add(new Tree(lexemeUnit.getLexeme()))
      lexemeUnit = null
      tree.add(parseVardcl())
    }
    else
      throw new Exception("Syntax Analyzer Error: \"var_sct\" was expected!")

    // TODOd: return the tree
    tree
  }

//  // var_dct =  ́var ́ var_dcl {  ́; ́ var_dcl }
  private def parseVardcl() = {
    // TODOd: create a tree with label "expression"
    val tree = new Tree("var_dcl")

    // TODOd: call getLexemeUnit
    getLexemeUnit()

    // TODOd: if token is NOT EOF
    if (lexemeUnit.getToken() != Token.EOF) {
      if (lexemeUnit.getToken() == Token.IDENTIFIER) {
        tree.add(new Tree(lexemeUnit.toString()))
        lexemeUnit = null
        getLexemeUnit()
      }

      if (lexemeUnit.getToken() == Token.IDENTIFIER) {
        tree.add(new Tree(lexemeUnit.toString()))
        lexemeUnit = null
        getLexemeUnit()
      } else if (lexemeUnit.getToken() == Token.COLON) {
        tree.add(parseType())
      }
      if (lexemeUnit.getToken() == Token.IDENTIFIER) {
        tree.add(new Tree(lexemeUnit.toString()))
        lexemeUnit = null
        getLexemeUnit()
      }

      if (lexemeUnit.getToken() == Token.COLON) {
        tree.add(new Tree(lexemeUnit.getLexeme()))
        lexemeUnit = null

        getLexemeUnit()
        tree.add(parseType())
        lexemeUnit = null
      } else
        throw new Exception("Syntax Analyzer Error: \"colon\" was expected!")

    } else if (lexemeUnit.getToken() != Token.EOF) {
      if (lexemeUnit.getToken() == Token.IDENTIFIER) {
        tree.add(new Tree(lexemeUnit.toString()))
        lexemeUnit = null
        getLexemeUnit()
      } else if (lexemeUnit.getToken() == Token.COLON) {
        tree.add(parseType())
      }

    }
    else
      throw new Exception("Syntax Analyzer Error: \"var_dcl\" was expected!")

    // TODOd: return the tree
    tree
  }

  private def parseType() = {
    // TODOd: create a tree with label "expression"
    val tree = new Tree("type")

    // TODOd: call getLexemeUnit
    getLexemeUnit()

    // TODOd: if token is NOT EOF, add result of "parseTerm" and "parseExpressionPrime" as new branches
    if (lexemeUnit.getToken() != Token.EOF) {
      if(lexemeUnit.getToken() == Token.INTEGER) {
        tree.add(new Tree(lexemeUnit.getLexeme()))
      } else
        throw new Exception("Syntax Analyzer Error: \"type\" was expected!")

    }
    else
      throw new Exception("Syntax Analyzer Error: \"type\" was expected!")

    // TODOd: return the tree
    tree
  }

}

object SyntaxAnalyzer {
  def main(args: Array[String]): Unit = {
    // check if source file was passed through the command-line
    if (args.length != 1) {
      print("Missing source file!")
      System.exit(1)
    }

    val syntaxAnalyzer = new SyntaxAnalyzer(args(0))
    val parseTree = syntaxAnalyzer.parse()
    print(parseTree)
  }
}
