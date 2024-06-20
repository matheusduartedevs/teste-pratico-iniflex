import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

class Pessoa {
    private String nome;
    private LocalDate dataNascimento;

    public Pessoa(String nome, LocalDate dataNascimento) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}

class Funcionario extends Pessoa {
    private BigDecimal salario;
    private String funcao;

    public Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        super(nome, dataNascimento);
        this.salario = salario;
        this.funcao = funcao;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public void aumentarSalario() {
        BigDecimal aumento = salario.multiply(BigDecimal.valueOf(0.1));
        salario = salario.add(aumento);
    }

    public BigDecimal calcularSalariosMinimos(BigDecimal salarioMinimo) {
        return salario.divide(salarioMinimo, 2, RoundingMode.HALF_UP);
    }

}

public class Main {
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
    private static final BigDecimal SALARIO_MINIMO = new BigDecimal("1212.00");
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        List<Funcionario> funcionarios = new ArrayList<>();

        // 3.1 – Inserir todos os funcionários, na mesma ordem e informações da tabela
        inserirFuncionarios(funcionarios);

        // 3.2 – Remover o funcionário “João” da lista
        removerFuncionarioPorNome(funcionarios, "João");

        // 3.3 Imprimir todos os funcionários com todas suas informações
        System.out.println("3.3 Imprimir todos os funcionários:");
        imprimirFuncionarios(funcionarios);

        // 3.4 Aumentar o salário dos funcionários em 10%
        aumentarSalarios(funcionarios);

        // 3.5 Agrupar os funcionários por função em um MAP
        Map<String, List<Funcionario>> funcionariosPorFuncao = agruparFuncionariosPorFuncao(funcionarios);

        // 3.6 Imprimir os funcionários agrupados por função
        System.out.println("\n3.6 Imprimir funcionários agrupados por função:");
        imprimirFuncionariosPorFuncao(funcionariosPorFuncao);

        // 3.8 Imprimir os funcionários que fazem aniversário nos meses 10 e 12
        System.out.println("\n3.8 Funcionários que fazem aniversário em outubro (10) e dezembro (12):");
        imprimirAniversariantesMes(funcionarios, 10);
        imprimirAniversariantesMes(funcionarios, 12);

        // 3.9 Imprimir o funcionário com a maior idade
        System.out.println("\n3.9 Funcionário com a maior idade:");
        imprimirFuncionarioMaiorIdade(funcionarios);

        // 3.10 Imprimir a lista de funcionários por ordem alfabética
        System.out.println("\n3.10 Lista de funcionários por ordem alfabética:");
        ordenarFuncionariosPorNome(funcionarios);
        imprimirFuncionarios(funcionarios);

        // 3.11 Imprimir o total dos salários dos funcionários
        System.out.println("\n3.11 Total dos salários dos funcionários: R$ " + calcularTotalSalarios(funcionarios));

        // 3.12 Imprimir quantos salários mínimos ganha cada funcionário
        System.out.println("\n3.12 Salários mínimos recebidos por cada funcionário:");
        imprimirSalariosMinimos(funcionarios);

        scanner.close();
    }

    public static void inserirFuncionarios(List<Funcionario> funcionarios) {
        System.out.println("Inserção de Funcionários:");
        while (true) {
            System.out.print("Nome do funcionário (ou 'fim' para encerrar): ");
            String nome = scanner.nextLine();

            if (nome.equalsIgnoreCase("fim")) {
                break;
            }

            LocalDate dataNascimento = null;
            boolean dataValida = false;
            while (!dataValida) {
                try {
                    System.out.print("Data de Nascimento (dd/MM/yyyy): ");
                    String dataStr = scanner.nextLine();
                    dataNascimento = LocalDate.parse(dataStr, dateFormatter);
                    dataValida = true;
                } catch (Exception e) {
                    System.out.println("Formato de data inválido. Digite no formato dd/MM/yyyy.");
                }
            }

            System.out.print("Salário: ");
            BigDecimal salario = scanner.nextBigDecimal();
            scanner.nextLine();

            System.out.print("Função: ");
            String funcao = scanner.nextLine();

            Funcionario funcionario = new Funcionario(nome, dataNascimento, salario, funcao);
            funcionarios.add(funcionario);

            System.out.println("Funcionário adicionado com sucesso!");
            System.out.println();
        }
    }

    public static void removerFuncionarioPorNome(List<Funcionario> funcionarios, String nome) {
        funcionarios.removeIf(funcionario -> funcionario.getNome().equalsIgnoreCase(nome));
    }

    public static void imprimirFuncionarios(List<Funcionario> funcionarios) {
        for (Funcionario funcionario : funcionarios) {
            String salarioFormatado = decimalFormat.format(funcionario.getSalario());

            System.out.println("Nome: " + funcionario.getNome());
            System.out.println("Data de Nascimento: " + funcionario.getDataNascimento().format(dateFormatter));
            System.out.println("Salário: R$ " + salarioFormatado);
            System.out.println("Função: " + funcionario.getFuncao());
            System.out.println();
        }
    }

    public static void aumentarSalarios(List<Funcionario> funcionarios) {
        for (Funcionario funcionario : funcionarios) {
            funcionario.aumentarSalario();
        }
    }

    public static Map<String, List<Funcionario>> agruparFuncionariosPorFuncao(List<Funcionario> funcionarios) {
        return funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));
    }

    public static void imprimirFuncionariosPorFuncao(Map<String, List<Funcionario>> funcionariosPorFuncao) {
        funcionariosPorFuncao.forEach((funcao, listaFuncionarios) -> {
            System.out.println("Função: " + funcao);
            for (Funcionario funcionario : listaFuncionarios) {
                System.out.println("  - " + funcionario.getNome() + " | Salário: R$ " + decimalFormat.format(funcionario.getSalario()));
            }
            System.out.println();
        });
    }

    public static void imprimirAniversariantesMes(List<Funcionario> funcionarios, int mes) {
        for (Funcionario funcionario : funcionarios) {
            if (funcionario.getDataNascimento().getMonthValue() == mes) {
                System.out.println("Nome: " + funcionario.getNome() + " | Data de Nascimento: " + funcionario.getDataNascimento().format(dateFormatter));
            }
        }
    }

    public static void imprimirFuncionarioMaiorIdade(List<Funcionario> funcionarios) {
        Optional<Funcionario> maisVelho = funcionarios.stream()
                .min(Comparator.comparing(funcionario -> funcionario.getDataNascimento()));

        if (maisVelho.isPresent()) {
            LocalDate hoje = LocalDate.now();
            long idade = maisVelho.get().getDataNascimento().until(hoje).getYears();
            System.out.println("Nome: " + maisVelho.get().getNome() + " | Idade: " + idade + " anos");
        }
    }

    public static void ordenarFuncionariosPorNome(List<Funcionario> funcionarios) {
        funcionarios.sort(Comparator.comparing(Funcionario::getNome));
    }

    public static BigDecimal calcularTotalSalarios(List<Funcionario> funcionarios) {
        BigDecimal totalSalarios = BigDecimal.ZERO;
        for (Funcionario funcionario : funcionarios) {
            totalSalarios = totalSalarios.add(funcionario.getSalario());
        }
        return totalSalarios;
    }

    public static void imprimirSalariosMinimos(List<Funcionario> funcionarios) {
        for (Funcionario funcionario : funcionarios) {
            BigDecimal salariosMinimos = funcionario.calcularSalariosMinimos(SALARIO_MINIMO);
            System.out.println(funcionario.getNome() + ": " + decimalFormat.format(salariosMinimos) + " salários mínimos");
        }
    }
}