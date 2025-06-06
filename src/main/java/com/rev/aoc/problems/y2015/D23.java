package com.rev.aoc.problems.y2015;

import com.rev.aoc.framework.problem.AocCoordinate;
import com.rev.aoc.framework.problem.AocProblem;
import com.rev.aoc.framework.problem.ProblemExecutionException;
import com.rev.aoc.util.emu.Cpu;

import java.util.List;

public final class D23 extends AocProblem<Long, Long> {

    private static final String HLF = "hlf";
    private static final String TPL = "tpl";
    private static final String INC = "inc";
    private static final String JMP = "jmp";
    private static final String JIE = "jie";
    private static final String JIO = "jio";

    @Override
    @SuppressWarnings("checkstyle:MagicNumber")
    public AocCoordinate getCoordinate() {
        return new AocCoordinate(2015, 23);
    }

    @Override
    protected Long partOneImpl() {
        final List<String> cpuInstructions = loadResources();
        final Cpu cpu = Cpu.create(List.of("a", "b"), cpuInstructions, D23::parseLine);
        cpu.run();
        return cpu.readRegister("b");
    }

    @Override
    protected Long partTwoImpl() {
        final List<String> cpuInstructions = loadResources();
        final Cpu cpu = Cpu.create(List.of("a", "b"), cpuInstructions, D23::parseLine);
        cpu.writeRegister("a", 1);
        cpu.run();
        return cpu.readRegister("b");
    }

    private static Cpu.Instruction parseLine(final String line) {
        final String instructionCode = line.substring(0, 3);
        return switch (instructionCode) {
            case HLF -> getHalf(line);
            case TPL -> getTriple(line);
            case INC -> getInc(line);
            case JMP -> getJump(line);
            case JIE -> getJumpIfEven(line);
            case JIO -> getJumpIfOne(line);
            default -> throw new ProblemExecutionException(String.format("Could not parse line '%s'", line));
        };
    }

    private static Cpu.Instruction getJumpIfEven(final String line) {
        final String[] split = line.split(",");
        final String registerName = split[0].split(" ")[1].trim();
        final int jumpAmount = Integer.parseInt(split[1].trim());
        return new Cpu.Instruction(cpu -> {
            if (cpu.readRegister(registerName) % 2 == 0) {
                return cpu.getIndex() + jumpAmount;
            }
            return cpu.getIndex() + 1;
        });
    }

    private static Cpu.Instruction getJumpIfOne(final String line) {
        final String[] split = line.split(",");
        final String registerName = split[0].split(" ")[1].trim();
        final int jumpAmount = Integer.parseInt(split[1].trim());
        return new Cpu.Instruction(cpu -> {
            if (cpu.readRegister(registerName) == 1) {
                return cpu.getIndex() + jumpAmount;
            }
            return cpu.getIndex() + 1;
        });
    }

    private static Cpu.Instruction getJump(final String line) {
        final String offsetStr = line.split(" ")[1].trim();
        final int offset = Integer.parseInt(offsetStr);
        return new Cpu.Instruction(cpu -> cpu.getIndex() + offset);
    }

    private static Cpu.Instruction getInc(final String line) {
        final String registerName = line.split(" ")[1].trim();
        return new Cpu.Instruction(cpu -> {
            long val = cpu.readRegister(registerName);
            cpu.writeRegister(registerName, val + 1);
            return cpu.getIndex() + 1;
        });
    }

    private static Cpu.Instruction getTriple(final String line) {
        final String registerName = line.split(" ")[1].trim();
        return new Cpu.Instruction(cpu -> {
            long val = cpu.readRegister(registerName);
            cpu.writeRegister(registerName, val * 3);
            return cpu.getIndex() + 1;
        });
    }

    private static Cpu.Instruction getHalf(final String line) {
        final String registerName = line.split(" ")[1].trim();
        return new Cpu.Instruction(cpu -> {
            long val = cpu.readRegister(registerName);
            cpu.writeRegister(registerName, val / 2);
            return cpu.getIndex() + 1;
        });
    }
}
