// Generated from Brainfuck.g4 by ANTLR 4.7.1

	package brainfuck.parser; 
	
	import brainfuck.*;
	import brainfuck.ast.*;
	import java.util.List;
	import java.io.File;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class BrainfuckParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, TAPE_INCREMENT=3, TAPE_DECREMENT=4, TAPE_LEFT=5, TAPE_RIGHT=6, 
		INPUT=7, OUTPUT=8, WHITESPACE=9;
	public static final int
		RULE_program = 0, RULE_command_list = 1, RULE_command = 2, RULE_loop = 3;
	public static final String[] ruleNames = {
		"program", "command_list", "command", "loop"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'['", "']'", "'+'", "'-'", "'<'", "'>'", "','", "'.'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, "TAPE_INCREMENT", "TAPE_DECREMENT", "TAPE_LEFT", "TAPE_RIGHT", 
		"INPUT", "OUTPUT", "WHITESPACE"
	};
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
	public String getGrammarFileName() { return "Brainfuck.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }


		private File file;
		
		public void setFile(File file){
			this.file = file;
		}
		
		public File getFile(){
			return file;
		}

	public BrainfuckParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgramContext extends ParserRuleContext {
		public Program prog;
		public Command_listContext commands;
		public TerminalNode EOF() { return getToken(BrainfuckParser.EOF, 0); }
		public Command_listContext command_list() {
			return getRuleContext(Command_listContext.class,0);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BrainfuckListener ) ((BrainfuckListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BrainfuckListener ) ((BrainfuckListener)listener).exitProgram(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BrainfuckVisitor ) return ((BrainfuckVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(8);
			((ProgramContext)_localctx).commands = command_list();
			setState(9);
			match(EOF);

			      SourceCorrespondence sc = new SourceCorrespondence(file, 0, 0, 0);
			      ((ProgramContext)_localctx).prog =  new Program(sc, ((ProgramContext)_localctx).commands.list);
			   
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Command_listContext extends ParserRuleContext {
		public ArrayList<Command> list;
		public CommandContext c;
		public List<CommandContext> command() {
			return getRuleContexts(CommandContext.class);
		}
		public CommandContext command(int i) {
			return getRuleContext(CommandContext.class,i);
		}
		public Command_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_command_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BrainfuckListener ) ((BrainfuckListener)listener).enterCommand_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BrainfuckListener ) ((BrainfuckListener)listener).exitCommand_list(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BrainfuckVisitor ) return ((BrainfuckVisitor<? extends T>)visitor).visitCommand_list(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Command_listContext command_list() throws RecognitionException {
		Command_listContext _localctx = new Command_listContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_command_list);

		      ((Command_listContext)_localctx).list =  new ArrayList<Command>();
		   
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(17);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << TAPE_INCREMENT) | (1L << TAPE_DECREMENT) | (1L << TAPE_LEFT) | (1L << TAPE_RIGHT) | (1L << INPUT) | (1L << OUTPUT))) != 0)) {
				{
				{
				setState(12);
				((Command_listContext)_localctx).c = command();
				_localctx.list.add(((Command_listContext)_localctx).c.value);
				}
				}
				setState(19);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CommandContext extends ParserRuleContext {
		public Command value;
		public LoopContext l;
		public Token ti;
		public Token td;
		public Token tl;
		public Token tr;
		public Token i;
		public Token o;
		public LoopContext loop() {
			return getRuleContext(LoopContext.class,0);
		}
		public TerminalNode TAPE_INCREMENT() { return getToken(BrainfuckParser.TAPE_INCREMENT, 0); }
		public TerminalNode TAPE_DECREMENT() { return getToken(BrainfuckParser.TAPE_DECREMENT, 0); }
		public TerminalNode TAPE_LEFT() { return getToken(BrainfuckParser.TAPE_LEFT, 0); }
		public TerminalNode TAPE_RIGHT() { return getToken(BrainfuckParser.TAPE_RIGHT, 0); }
		public TerminalNode INPUT() { return getToken(BrainfuckParser.INPUT, 0); }
		public TerminalNode OUTPUT() { return getToken(BrainfuckParser.OUTPUT, 0); }
		public CommandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_command; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BrainfuckListener ) ((BrainfuckListener)listener).enterCommand(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BrainfuckListener ) ((BrainfuckListener)listener).exitCommand(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BrainfuckVisitor ) return ((BrainfuckVisitor<? extends T>)visitor).visitCommand(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CommandContext command() throws RecognitionException {
		CommandContext _localctx = new CommandContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_command);
		try {
			setState(35);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__0:
				enterOuterAlt(_localctx, 1);
				{
				setState(20);
				((CommandContext)_localctx).l = loop();

				      long startPos = _ctx.getStart().getCharPositionInLine();
				      long stopPos = _ctx.getStop() != null ? _ctx.getStop().getCharPositionInLine() : startPos + 1;
				      SourceCorrespondence sc = new SourceCorrespondence(file, _ctx.getStart().getLine(), startPos, (stopPos-startPos));
				      ((CommandContext)_localctx).value =  new LoopCommand(sc, ((CommandContext)_localctx).l.list);
				   
				}
				break;
			case TAPE_INCREMENT:
				enterOuterAlt(_localctx, 2);
				{
				setState(23);
				((CommandContext)_localctx).ti = match(TAPE_INCREMENT);

				      long startPos = _ctx.getStart().getCharPositionInLine();
				      long stopPos = _ctx.getStop() != null ? _ctx.getStop().getCharPositionInLine() : startPos + 1;
				      SourceCorrespondence sc = new SourceCorrespondence(file, _ctx.getStart().getLine(), startPos, (stopPos-startPos));
				      ((CommandContext)_localctx).value =  new IncrementCommand(sc);
				   
				}
				break;
			case TAPE_DECREMENT:
				enterOuterAlt(_localctx, 3);
				{
				setState(25);
				((CommandContext)_localctx).td = match(TAPE_DECREMENT);

				      long startPos = _ctx.getStart().getCharPositionInLine();
				      long stopPos = _ctx.getStop() != null ? _ctx.getStop().getCharPositionInLine() : startPos + 1;
				      SourceCorrespondence sc = new SourceCorrespondence(file, _ctx.getStart().getLine(), startPos, (stopPos-startPos));
				      ((CommandContext)_localctx).value =  new DecrementCommand(sc);
				   
				}
				break;
			case TAPE_LEFT:
				enterOuterAlt(_localctx, 4);
				{
				setState(27);
				((CommandContext)_localctx).tl = match(TAPE_LEFT);

				      long startPos = _ctx.getStart().getCharPositionInLine();
				      long stopPos = _ctx.getStop() != null ? _ctx.getStop().getCharPositionInLine() : startPos + 1;
				      SourceCorrespondence sc = new SourceCorrespondence(file, _ctx.getStart().getLine(), startPos, (stopPos-startPos));
				      ((CommandContext)_localctx).value =  new MoveLeftCommand(sc);
				   
				}
				break;
			case TAPE_RIGHT:
				enterOuterAlt(_localctx, 5);
				{
				setState(29);
				((CommandContext)_localctx).tr = match(TAPE_RIGHT);

				      long startPos = _ctx.getStart().getCharPositionInLine();
				      long stopPos = _ctx.getStop() != null ? _ctx.getStop().getCharPositionInLine() : startPos + 1;
				      SourceCorrespondence sc = new SourceCorrespondence(file, _ctx.getStart().getLine(), startPos, (stopPos-startPos));
				      ((CommandContext)_localctx).value =  new MoveRightCommand(sc);
				   
				}
				break;
			case INPUT:
				enterOuterAlt(_localctx, 6);
				{
				setState(31);
				((CommandContext)_localctx).i = match(INPUT);

				      long startPos = _ctx.getStart().getCharPositionInLine();
				      long stopPos = _ctx.getStop() != null ? _ctx.getStop().getCharPositionInLine() : startPos + 1;
				      SourceCorrespondence sc = new SourceCorrespondence(file, _ctx.getStart().getLine(), startPos, (stopPos-startPos));
				      ((CommandContext)_localctx).value =  new InputCommand(sc);
				   
				}
				break;
			case OUTPUT:
				enterOuterAlt(_localctx, 7);
				{
				setState(33);
				((CommandContext)_localctx).o = match(OUTPUT);

				      long startPos = _ctx.getStart().getCharPositionInLine();
				      long stopPos = _ctx.getStop() != null ? _ctx.getStop().getCharPositionInLine() : startPos + 1;
				      SourceCorrespondence sc = new SourceCorrespondence(file, _ctx.getStart().getLine(), startPos, (stopPos-startPos));
				      ((CommandContext)_localctx).value =  new OutputCommand(sc);
				   
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LoopContext extends ParserRuleContext {
		public ArrayList<Command> list;
		public Command_listContext commands;
		public Command_listContext command_list() {
			return getRuleContext(Command_listContext.class,0);
		}
		public LoopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_loop; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BrainfuckListener ) ((BrainfuckListener)listener).enterLoop(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BrainfuckListener ) ((BrainfuckListener)listener).exitLoop(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BrainfuckVisitor ) return ((BrainfuckVisitor<? extends T>)visitor).visitLoop(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LoopContext loop() throws RecognitionException {
		LoopContext _localctx = new LoopContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_loop);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(37);
			match(T__0);
			setState(38);
			((LoopContext)_localctx).commands = command_list();
			setState(39);
			match(T__1);

			      ((LoopContext)_localctx).list =  ((LoopContext)_localctx).commands.list;
			   
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\13-\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\3\2\3\2\3\2\3\2\3\3\3\3\3\3\7\3\22\n\3\f\3\16\3\25"+
		"\13\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4"+
		"&\n\4\3\5\3\5\3\5\3\5\3\5\3\5\2\2\6\2\4\6\b\2\2\2/\2\n\3\2\2\2\4\23\3"+
		"\2\2\2\6%\3\2\2\2\b\'\3\2\2\2\n\13\5\4\3\2\13\f\7\2\2\3\f\r\b\2\1\2\r"+
		"\3\3\2\2\2\16\17\5\6\4\2\17\20\b\3\1\2\20\22\3\2\2\2\21\16\3\2\2\2\22"+
		"\25\3\2\2\2\23\21\3\2\2\2\23\24\3\2\2\2\24\5\3\2\2\2\25\23\3\2\2\2\26"+
		"\27\5\b\5\2\27\30\b\4\1\2\30&\3\2\2\2\31\32\7\5\2\2\32&\b\4\1\2\33\34"+
		"\7\6\2\2\34&\b\4\1\2\35\36\7\7\2\2\36&\b\4\1\2\37 \7\b\2\2 &\b\4\1\2!"+
		"\"\7\t\2\2\"&\b\4\1\2#$\7\n\2\2$&\b\4\1\2%\26\3\2\2\2%\31\3\2\2\2%\33"+
		"\3\2\2\2%\35\3\2\2\2%\37\3\2\2\2%!\3\2\2\2%#\3\2\2\2&\7\3\2\2\2\'(\7\3"+
		"\2\2()\5\4\3\2)*\7\4\2\2*+\b\5\1\2+\t\3\2\2\2\4\23%";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}