// Generated from Brainfuck.g4 by ANTLR 4.7.1

package com.benjholla.brainfuck.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.NoViableAltException;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.RuntimeMetaData;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.VocabularyImpl;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;

import com.benjholla.brainfuck.ast.DecrementInstruction;
import com.benjholla.brainfuck.ast.IncrementInstruction;
import com.benjholla.brainfuck.ast.Instruction;
import com.benjholla.brainfuck.ast.LoopInstruction;
import com.benjholla.brainfuck.ast.MoveLeftInstruction;
import com.benjholla.brainfuck.ast.MoveRightInstruction;
import com.benjholla.brainfuck.ast.Program;
import com.benjholla.brainfuck.ast.ReadInputInstruction;
import com.benjholla.brainfuck.ast.WriteOutputInstruction;
import com.benjholla.brainfuck.parser.support.ParserSourceCorrespondence;

@SuppressWarnings({ "all", "warnings", "unchecked", "unused", "cast" })
public class BrainfuckParser extends Parser {
	static {
		RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION);
	}

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache = new PredictionContextCache();
	public static final int T__0 = 1, T__1 = 2, TAPE_INCREMENT = 3, TAPE_DECREMENT = 4, TAPE_LEFT = 5, TAPE_RIGHT = 6,
			INPUT = 7, OUTPUT = 8, WHITESPACE = 9;
	public static final int RULE_program = 0, RULE_instruction_list = 1, RULE_instruction = 2, RULE_loop = 3;
	public static final String[] ruleNames = { "program", "instruction_list", "instruction", "loop" };

	private static final String[] _LITERAL_NAMES = { null, "'['", "']'", "'+'", "'-'", "'<'", "'>'", "','", "'.'" };
	private static final String[] _SYMBOLIC_NAMES = { null, null, null, "TAPE_INCREMENT", "TAPE_DECREMENT", "TAPE_LEFT",
			"TAPE_RIGHT", "INPUT", "OUTPUT", "WHITESPACE" };
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() {
		return "Brainfuck.g4";
	}

	@Override
	public String[] getRuleNames() {
		return ruleNames;
	}

	@Override
	public String getSerializedATN() {
		return _serializedATN;
	}

	@Override
	public ATN getATN() {
		return _ATN;
	}

	private File file;

	public void setFile(File file) {
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	public BrainfuckParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
	}

	public static class ProgramContext extends ParserRuleContext {
		public Program prog;
		public Instruction_listContext instructions;
		public Token eof;

		public Instruction_listContext instruction_list() {
			return getRuleContext(Instruction_listContext.class, 0);
		}

		public TerminalNode EOF() {
			return getToken(BrainfuckParser.EOF, 0);
		}

		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_program;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof BrainfuckListener)
				((BrainfuckListener) listener).enterProgram(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof BrainfuckListener)
				((BrainfuckListener) listener).exitProgram(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof BrainfuckVisitor)
				return ((BrainfuckVisitor<? extends T>) visitor).visitProgram(this);
			else
				return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(8);
				((ProgramContext) _localctx).instructions = instruction_list();
				setState(9);
				((ProgramContext) _localctx).eof = match(EOF);

				ParserSourceCorrespondence sc;
				if (((ProgramContext) _localctx).instructions.list.isEmpty()) {
					sc = new ParserSourceCorrespondence(file, 0, 0, 0, 0);
				} else {
					ParserSourceCorrespondence firstInstructionSC = ((ProgramContext) _localctx).instructions.list
							.get(0).getParserSourceCorrespondence();
					sc = new ParserSourceCorrespondence(file, firstInstructionSC.getOffset(),
							((int) ((ProgramContext) _localctx).eof.getStartIndex()
									- (int) firstInstructionSC.getOffset()),
							firstInstructionSC.getStartLine(), ((ProgramContext) _localctx).eof.getLine());
				}
				((ProgramContext) _localctx).prog = new Program(sc, ((ProgramContext) _localctx).instructions.list);

			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Instruction_listContext extends ParserRuleContext {
		public ArrayList<Instruction> list;
		public InstructionContext c;

		public List<InstructionContext> instruction() {
			return getRuleContexts(InstructionContext.class);
		}

		public InstructionContext instruction(int i) {
			return getRuleContext(InstructionContext.class, i);
		}

		public Instruction_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_instruction_list;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof BrainfuckListener)
				((BrainfuckListener) listener).enterInstruction_list(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof BrainfuckListener)
				((BrainfuckListener) listener).exitInstruction_list(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof BrainfuckVisitor)
				return ((BrainfuckVisitor<? extends T>) visitor).visitInstruction_list(this);
			else
				return visitor.visitChildren(this);
		}
	}

	public final Instruction_listContext instruction_list() throws RecognitionException {
		Instruction_listContext _localctx = new Instruction_listContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_instruction_list);

		((Instruction_listContext) _localctx).list = new ArrayList<Instruction>();

		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(17);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0
						&& ((1L << _la) & ((1L << T__0) | (1L << TAPE_INCREMENT) | (1L << TAPE_DECREMENT)
								| (1L << TAPE_LEFT) | (1L << TAPE_RIGHT) | (1L << INPUT) | (1L << OUTPUT))) != 0)) {
					{
						{
							setState(12);
							((Instruction_listContext) _localctx).c = instruction();
							_localctx.list.add(((Instruction_listContext) _localctx).c.value);
						}
					}
					setState(19);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InstructionContext extends ParserRuleContext {
		public Instruction value;
		public LoopContext l;
		public Token tape_increment;
		public Token tape_decrement;
		public Token tape_left;
		public Token tape_right;
		public Token input;
		public Token output;

		public LoopContext loop() {
			return getRuleContext(LoopContext.class, 0);
		}

		public TerminalNode TAPE_INCREMENT() {
			return getToken(BrainfuckParser.TAPE_INCREMENT, 0);
		}

		public TerminalNode TAPE_DECREMENT() {
			return getToken(BrainfuckParser.TAPE_DECREMENT, 0);
		}

		public TerminalNode TAPE_LEFT() {
			return getToken(BrainfuckParser.TAPE_LEFT, 0);
		}

		public TerminalNode TAPE_RIGHT() {
			return getToken(BrainfuckParser.TAPE_RIGHT, 0);
		}

		public TerminalNode INPUT() {
			return getToken(BrainfuckParser.INPUT, 0);
		}

		public TerminalNode OUTPUT() {
			return getToken(BrainfuckParser.OUTPUT, 0);
		}

		public InstructionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_instruction;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof BrainfuckListener)
				((BrainfuckListener) listener).enterInstruction(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof BrainfuckListener)
				((BrainfuckListener) listener).exitInstruction(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof BrainfuckVisitor)
				return ((BrainfuckVisitor<? extends T>) visitor).visitInstruction(this);
			else
				return visitor.visitChildren(this);
		}
	}

	public final InstructionContext instruction() throws RecognitionException {
		InstructionContext _localctx = new InstructionContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_instruction);
		try {
			setState(35);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__0:
				enterOuterAlt(_localctx, 1); {
				setState(20);
				((InstructionContext) _localctx).l = loop();

				((InstructionContext) _localctx).value = ((InstructionContext) _localctx).l.value;

			}
				break;
			case TAPE_INCREMENT:
				enterOuterAlt(_localctx, 2); {
				setState(23);
				((InstructionContext) _localctx).tape_increment = match(TAPE_INCREMENT);

				ParserSourceCorrespondence sc = new ParserSourceCorrespondence(file,
						((InstructionContext) _localctx).tape_increment.getStartIndex(),
						((InstructionContext) _localctx).tape_increment.getText().length(),
						((InstructionContext) _localctx).tape_increment.getLine(),
						((InstructionContext) _localctx).tape_increment.getLine());
				((InstructionContext) _localctx).value = new IncrementInstruction(sc);

			}
				break;
			case TAPE_DECREMENT:
				enterOuterAlt(_localctx, 3); {
				setState(25);
				((InstructionContext) _localctx).tape_decrement = match(TAPE_DECREMENT);

				ParserSourceCorrespondence sc = new ParserSourceCorrespondence(file,
						((InstructionContext) _localctx).tape_decrement.getStartIndex(),
						((InstructionContext) _localctx).tape_decrement.getText().length(),
						((InstructionContext) _localctx).tape_decrement.getLine(),
						((InstructionContext) _localctx).tape_decrement.getLine());
				((InstructionContext) _localctx).value = new DecrementInstruction(sc);

			}
				break;
			case TAPE_LEFT:
				enterOuterAlt(_localctx, 4); {
				setState(27);
				((InstructionContext) _localctx).tape_left = match(TAPE_LEFT);

				ParserSourceCorrespondence sc = new ParserSourceCorrespondence(file,
						((InstructionContext) _localctx).tape_left.getStartIndex(),
						((InstructionContext) _localctx).tape_left.getText().length(),
						((InstructionContext) _localctx).tape_left.getLine(),
						((InstructionContext) _localctx).tape_left.getLine());
				((InstructionContext) _localctx).value = new MoveLeftInstruction(sc);

			}
				break;
			case TAPE_RIGHT:
				enterOuterAlt(_localctx, 5); {
				setState(29);
				((InstructionContext) _localctx).tape_right = match(TAPE_RIGHT);

				ParserSourceCorrespondence sc = new ParserSourceCorrespondence(file,
						((InstructionContext) _localctx).tape_right.getStartIndex(),
						((InstructionContext) _localctx).tape_right.getText().length(),
						((InstructionContext) _localctx).tape_right.getLine(),
						((InstructionContext) _localctx).tape_right.getLine());
				((InstructionContext) _localctx).value = new MoveRightInstruction(sc);

			}
				break;
			case INPUT:
				enterOuterAlt(_localctx, 6); {
				setState(31);
				((InstructionContext) _localctx).input = match(INPUT);

				ParserSourceCorrespondence sc = new ParserSourceCorrespondence(file,
						((InstructionContext) _localctx).input.getStartIndex(),
						((InstructionContext) _localctx).input.getText().length(),
						((InstructionContext) _localctx).input.getLine(),
						((InstructionContext) _localctx).input.getLine());
				((InstructionContext) _localctx).value = new ReadInputInstruction(sc);

			}
				break;
			case OUTPUT:
				enterOuterAlt(_localctx, 7); {
				setState(33);
				((InstructionContext) _localctx).output = match(OUTPUT);

				ParserSourceCorrespondence sc = new ParserSourceCorrespondence(file,
						((InstructionContext) _localctx).output.getStartIndex(),
						((InstructionContext) _localctx).output.getText().length(),
						((InstructionContext) _localctx).output.getLine(),
						((InstructionContext) _localctx).output.getLine());
				((InstructionContext) _localctx).value = new WriteOutputInstruction(sc);

			}
				break;
			default:
				throw new NoViableAltException(this);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LoopContext extends ParserRuleContext {
		public LoopInstruction value;
		public Token lbrace;
		public Instruction_listContext instructions;
		public Token rbrace;

		public Instruction_listContext instruction_list() {
			return getRuleContext(Instruction_listContext.class, 0);
		}

		public LoopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_loop;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof BrainfuckListener)
				((BrainfuckListener) listener).enterLoop(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof BrainfuckListener)
				((BrainfuckListener) listener).exitLoop(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof BrainfuckVisitor)
				return ((BrainfuckVisitor<? extends T>) visitor).visitLoop(this);
			else
				return visitor.visitChildren(this);
		}
	}

	public final LoopContext loop() throws RecognitionException {
		LoopContext _localctx = new LoopContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_loop);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(37);
				((LoopContext) _localctx).lbrace = match(T__0);
				setState(38);
				((LoopContext) _localctx).instructions = instruction_list();
				setState(39);
				((LoopContext) _localctx).rbrace = match(T__1);

				int length = (((LoopContext) _localctx).rbrace.getStartIndex()
						- ((LoopContext) _localctx).lbrace.getStartIndex()) + 1;
				ParserSourceCorrespondence sc = new ParserSourceCorrespondence(file,
						((LoopContext) _localctx).lbrace.getStartIndex(), length,
						((LoopContext) _localctx).lbrace.getLine(), ((LoopContext) _localctx).rbrace.getLine());
				((LoopContext) _localctx).value = new LoopInstruction(sc, ((LoopContext) _localctx).instructions.list);

			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN = "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\13-\4\2\t\2\4\3\t"
			+ "\3\4\4\t\4\4\5\t\5\3\2\3\2\3\2\3\2\3\3\3\3\3\3\7\3\22\n\3\f\3\16\3\25"
			+ "\13\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4"
			+ "&\n\4\3\5\3\5\3\5\3\5\3\5\3\5\2\2\6\2\4\6\b\2\2\2/\2\n\3\2\2\2\4\23\3"
			+ "\2\2\2\6%\3\2\2\2\b\'\3\2\2\2\n\13\5\4\3\2\13\f\7\2\2\3\f\r\b\2\1\2\r"
			+ "\3\3\2\2\2\16\17\5\6\4\2\17\20\b\3\1\2\20\22\3\2\2\2\21\16\3\2\2\2\22"
			+ "\25\3\2\2\2\23\21\3\2\2\2\23\24\3\2\2\2\24\5\3\2\2\2\25\23\3\2\2\2\26"
			+ "\27\5\b\5\2\27\30\b\4\1\2\30&\3\2\2\2\31\32\7\5\2\2\32&\b\4\1\2\33\34"
			+ "\7\6\2\2\34&\b\4\1\2\35\36\7\7\2\2\36&\b\4\1\2\37 \7\b\2\2 &\b\4\1\2!"
			+ "\"\7\t\2\2\"&\b\4\1\2#$\7\n\2\2$&\b\4\1\2%\26\3\2\2\2%\31\3\2\2\2%\33"
			+ "\3\2\2\2%\35\3\2\2\2%\37\3\2\2\2%!\3\2\2\2%#\3\2\2\2&\7\3\2\2\2\'(\7\3"
			+ "\2\2()\5\4\3\2)*\7\4\2\2*+\b\5\1\2+\t\3\2\2\2\4\23%";
	public static final ATN _ATN = new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}