package conexao;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SistemaDAO dao = new SistemaDAO();
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n========= MENU ACADÊMICO =========");
            System.out.println("1 - Cadastrar Usuário");
            System.out.println("2 - Cadastrar Instituição");
            System.out.println("3 - Cadastrar Curso");
            System.out.println("4 - Cadastrar Professor");
            System.out.println("5 - Filtrar Cursos por Instituição");
            System.out.println("6 - Listar todas as instituições");
            System.out.println("7 - Listar todos os cursos");
            System.out.println("8 - Listar todos os Professores");
            System.out.println("9 - Filtrar Instituição por Nome");
            System.out.println("10 - Filtrar Professor por Matéria");
            System.out.println("11 - Apagar uma Instituição");
            System.out.println("12 - Atualizar Dados de uma Instituição");
            System.out.println("13 - Atualizar Dados de um Curso");
            System.out.println("14 - Vincular Professor a uma Instituição");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("\n--- NOVO USUÁRIO ---");
                    System.out.print("Email: ");
                    String novoEmail = sc.nextLine();
                    System.out.print("Senha: ");
                    String novaSenha = sc.nextLine();
                    dao.cadastrarUsuario(novoEmail, novaSenha);
                    break;
                case 2:
                    System.out.print("Nome: ");
                    String ni = sc.nextLine();
                    System.out.print("CNPJ: ");
                    String cj = sc.nextLine();
                    System.out.print("Endereço: ");
                    String ed = sc.nextLine();
                    dao.cadastrarInstituicao(ni, cj, ed);
                    break;
                case 3:
                    System.out.print("ID da Instituição: ");
                    int idI = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Nome do Curso: ");
                    String nc = sc.nextLine();
                    System.out.print("Duração (semestres): ");
                    int dur = sc.nextInt();
                    dao.cadastrarCurso(idI, nc, dur);
                    break;
                case 4:
                    System.out.print("ID do Usuário já cadastrado: ");
                    int idU = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Confirme o Email: ");
                    String emailP = sc.nextLine();
                    System.out.print("Nome Completo: ");
                    String nomeP = sc.nextLine();
                    System.out.print("Matérias: ");
                    String matP = sc.nextLine();
                    dao.cadastrarProfessor(idU, emailP, nomeP, matP);
                    break;
                case 5:
                    System.out.print("Nome da Instituição para busca: ");
                    String busca = sc.nextLine();
                    dao.filtrarCursosPorInstituicao(busca);
                    break;
                case 6:
                    dao.listarTodasInstituicoes();
                    break;
                case 7:
                    dao.listarTodosCursos();
                    break;
                case 8:
                    dao.listarProfessoresEInstituicoes();
                    break;
                case 9:
                    System.out.print("Nome da Instituição: ");
                    String buscaInst = sc.nextLine();
                    dao.filtrarInstituicaoPorNome(buscaInst);
                    break;
                case 10:
                    System.out.print("Matéria/Especialidade: ");
                    String buscaMat = sc.nextLine();
                    dao.filtrarProfessorPorMateria(buscaMat);
                    break;
                case 11:
                    System.out.print("ID da Instituição para apagar: ");
                    int idApagar = sc.nextInt();
                    dao.apagarInstituicao(idApagar);
                    break;
                case 12:
                    System.out.print("ID da Inst. para alterar: ");
                    int idUpI = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Novo Nome: ");
                    String nNomeI = sc.nextLine();
                    System.out.print("Novo Endereço: ");
                    String nEndI = sc.nextLine();
                    dao.atualizarInstituicao(idUpI, nNomeI, nEndI);
                    break;
                case 13:
                    System.out.print("ID do Curso para alterar: ");
                    int idUpC = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Novo Nome do Curso: ");
                    String nNomeC = sc.nextLine();
                    System.out.print("Nova Duração: ");
                    int nDur = sc.nextInt();
                    dao.atualizarCurso(idUpC, nNomeC, nDur);
                    break;
                case 14:
                    System.out.print("Digite o ID do PROFESSOR: ");
                    int idP = sc.nextInt();
                    System.out.print("Digite o ID da INSTITUIÇÃO: ");
                    int idInst = sc.nextInt();

                    dao.vincularProfessorInstituicao(idP, idInst);
                    break;
            }
        }
        sc.close();
    }
}
