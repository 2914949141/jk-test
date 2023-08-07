package com.du.controller;

import cn.hutool.core.util.ObjectUtil;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.assertj.core.util.Lists;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CapacityCalUtil {

    public static void main(String[] args) {
        Elf build = Elf.builder()
                .attack(Elf.Ability.builder()
                        .var(70)
                        .learnVar(0)
                        .varCoefficient(0.9)
                        .build())
                .defense(
                        Elf.Ability.builder()
                                .var(117)
                                .learnVar(0)
                                .varCoefficient(1.0)
                                .build())
                .specialAttack(
                        Elf.Ability.builder()
                                .var(130)
                                .learnVar(0)
                                .varCoefficient(1.0)
                                .build())
                .specialDefense(
                        Elf.Ability.builder()
                                .var(117)
                                .learnVar(0)
                                .varCoefficient(1.0)
                                .build())
                .velocity(
                        Elf.Ability.builder()
                                .var(130)
                                .learnVar(0)
                                .varCoefficient(1.1)
                                .build())
                .physical(
                        Elf.Ability.builder()
                                .var(171)
                                .learnVar(0)
                                .build())
                .build();
        long l = System.currentTimeMillis();
        List<Elf> elfList = build.calReverse();
        long r = System.currentTimeMillis();
        System.out.println(r - l);
    }

    @Builder
    @Data
    @ToString
    static class Elf implements Serializable {
        private static final int SIG_ABILITY_MAX = 255;
        private static final int ALL_ABILITY_MAX = 510;
        /**
         * 攻击 系数
         */
        private Ability attack;
        /**
         * 防御
         */
        private Ability defense;
        /**
         * 特殊防御
         */
        private Ability specialDefense;
        /**
         * 特殊攻击
         */
        private Ability specialAttack;
        /**
         * 速度
         */
        private Ability velocity;
        /**
         * 体力
         */
        private Ability physical;

        /**
         * 单项能力值计算
         *
         * @param var
         * @param learn_var
         * @param ability
         * @param is_physical
         * @return
         */
        int cal(Integer var, Integer learn_var, Double ability, Boolean is_physical) {
            int calculate = 0;
            BigDecimal base = BigDecimal.valueOf(
                            var).multiply(BigDecimal.valueOf(2))
                    .add(
                            BigDecimal.valueOf(learn_var).divide(BigDecimal.valueOf(4), 2, BigDecimal.ROUND_HALF_UP));
            if (is_physical) {
                // 体力能力值= Int [(种族值×2+学习力÷4+100+个体)+10]
                calculate = base.add(BigDecimal.valueOf(
                                100 + 31 + 10
                        ))
                        .setScale(0, BigDecimal.ROUND_FLOOR).intValue();
            } else {
                // 五项能力值=Int[((种族值×2+学习力÷4+个体)+5)×性格修正]
                calculate = base.add(BigDecimal.valueOf(
                                31 + 5))
                        .multiply(
                                BigDecimal.valueOf(ability))
                        .setScale(0, BigDecimal.ROUND_FLOOR).intValue();
            }
            return calculate;
        }

        /**
         * 单项能力值计算
         *
         * @param sig
         * @return
         */
        int cal(Function<Elf, Ability> sig) {
            Ability ability = sig.apply(this);
            return this.cal(ability);
        }

        /**
         * 单项能力值计算
         *
         * @param
         * @return
         */
        int cal(Ability ability) {
            if (Objects.isNull(ability)) {
                return 0;
            }
            return ability.finalVar = cal(ability.var, ability.learnVar, ability.varCoefficient, Objects.isNull(ability.varCoefficient));
        }

        /**
         * 计算全部能力值
         *
         * @return
         */
        Elf cal() {
            this.cal(Elf::getAttack);
            this.cal(Elf::getDefense);
            this.cal(Elf::getPhysical);
            this.cal(Elf::getVelocity);
            this.cal(Elf::getSpecialAttack);
            this.cal(Elf::getSpecialDefense);
            return this;
        }

        /**
         * 逆向推算
         *
         * @return
         */
        List<Elf> calReverse() {
            // 暴力求解
            // 可设置排除项，默认排除系数低于1.0的项
            List<Ability> abilityAllList = Lists.newArrayList(attack, defense, physical, velocity, specialAttack, specialDefense);
            List<Ability> hostAbility = abilityAllList.stream().filter(f -> Objects.nonNull(f.varCoefficient) && f.varCoefficient == 1.1).collect(Collectors.toList());
            // 最优单项给极限值
            this.calSigMaxReverse(hostAbility);
            // 其它适配组合取最高值
            List<Ability> abilityList = abilityAllList.stream().filter(f -> Objects.isNull(f.varCoefficient) || f.varCoefficient == 1).collect(Collectors.toList());
            List<Elf> elfList = Lists.newArrayList();
            generateCombinations(abilityList, elfList);
            return elfList;
        }


        void generateCombinations(List<Ability> abilityList, List<Elf> data) {
            generate(0, abilityList, data);
        }

        void generate(int floor, List<Ability> abilityList, List<Elf> data) {

            if (floor == abilityList.size()) {
                int learnVarSum = this.sumLearn();
                if (learnVarSum == ALL_ABILITY_MAX) {
                    this.sum();
                    data.add(ObjectUtil.cloneByStream(this));
                }
                return;
            }

            for (int i = 0; i <= SIG_ABILITY_MAX; i++) {
                // 给一个值
                Ability ability = abilityList.get(floor);
                int curr_ability = this.cal(ability);
                ability.learnVar = i;
                int last_ability = this.cal(ability);
                if (ability.learnVar != 0 && curr_ability == last_ability) {
                    // 增加学习力没有出现数值上的增加，则忽略后续的操作
                    continue;
                }
                // 继续给下一个赋值
                generate(floor + 1, abilityList, data);
            }
        }

        void calSigMaxReverse(List<Ability> hostAbility) {
            for (Ability ability : hostAbility) {
                ability.learnVar = SIG_ABILITY_MAX;
                int max = 0;
                while (true) {
                    int curr_max = this.cal(ability);
                    if (max == 0) {
                        max = curr_max;
                    }
                    if (curr_max < max) {
                        ability.learnVar++;
                        this.cal(ability);
                        break;
                    }
                    ability.learnVar--;
                }
            }
        }

        void calSigMinReverse(List<Ability> hostAbility) {
            for (Ability ability : hostAbility) {
                ability.learnVar = 0;
                int max = 0;
                while (true) {
                    int curr_max = this.cal(ability);
                    if (max == 0) {
                        max = curr_max;
                    }
                    if (curr_max > max) {
                        this.cal(ability);
                        break;
                    }
                    ability.learnVar++;
                }
            }
        }

        void calAllSigMaxReverse() {
            this.calSigMaxReverse(Lists.newArrayList(attack, defense, physical, velocity, specialAttack, specialDefense));
        }

        /**
         * 计算能力值总和
         *
         * @return
         */
        int sum() {
            this.cal();
            return attack.finalVar + defense.finalVar + physical.finalVar + velocity.finalVar + specialAttack.finalVar + specialDefense.finalVar;
        }

        int sumLearn() {
            return Lists.newArrayList(attack, defense, physical, velocity, specialAttack, specialDefense).stream().filter(o -> Objects.nonNull(o.learnVar)).mapToInt(o -> o.learnVar).sum();
        }

        @Builder
        @Data
        @ToString
        static class Ability implements Serializable {
            // 能力值
            private Integer var;
            // 学习力
            private Integer learnVar;
            // 系数
            private Double varCoefficient;
            // 最终值
            private int finalVar;
        }
    }
}
