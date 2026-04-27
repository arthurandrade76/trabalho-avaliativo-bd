package conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class SistemaDAO {

    public void cadastrarUsuario(String email, String senha) {
        String sql = "INSERT INTO usuario (email, senha) VALUES (?, ?)";

        try (Connection conn = Conexão.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, senha);

            stmt.executeUpdate();
            System.out.println("Usuário " + email + " cadastrado com sucesso!");

        } catch (SQLException e) {
            if (e.getMessage().contains("duplicate key")) {
                System.err.println("Erro: Este email já está cadastrado.");
            } else {
                System.err.println("Erro ao cadastrar usuário: " + e.getMessage());
            }
        }
    }

    public void cadastrarInstituicao(String nome, String cnpj, String endereco) {
        String sql = "INSERT INTO instituicao (nome, cnpj, endereco) VALUES (?, ?, ?)";
        try (Connection conn = Conexão.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, cnpj);
            stmt.setString(3, endereco);
            stmt.executeUpdate();
            System.out.println("Instituição cadastrada!");
        } catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    public void cadastrarCurso(int idInst, String nome, int duracao) {
        String sql = "INSERT INTO curso (fk_id_instituicao, nome_curso, duracao) VALUES (?, ?, ?)";
        try (Connection conn = Conexão.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idInst);
            stmt.setString(2, nome);
            stmt.setInt(3, duracao);
            stmt.executeUpdate();
            System.out.println("Curso cadastrado!");
        } catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    public void cadastrarProfessor(int idUsuario, String email, String nome, String materias) {

        String sql = "INSERT INTO professor (fk_id_usuario, email, nome, materias) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexão.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            stmt.setString(2, email);
            stmt.setString(3, nome);
            stmt.setString(4, materias);

            stmt.executeUpdate();
            System.out.println("Dados do professor " + nome + " salvos com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar dados do professor: " + e.getMessage());
        }
    }

    public void vincularProfessorInstituicao(int idProf, int idInst) {
        String sql = "UPDATE professor SET fk_id_instituicao = ? WHERE id_professor = ?";

        try (Connection conn = Conexão.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idInst);
            stmt.setInt(2, idProf);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Professor vinculado com sucesso à instituição!");
            } else {
                System.out.println("Professor não encontrado. Verifique o ID.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao vincular: " + e.getMessage());
        }
    }

    public void filtrarCursosPorInstituicao(String nomeInst) {
        String sql = "SELECT c.nome_curso, c.duracao, i.nome "
                + "FROM curso c JOIN instituicao i ON c.fk_id_instituicao = i.id_instituicao "
                + "WHERE i.nome ILIKE ?";

        try (Connection conn = Conexão.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nomeInst + "%");
            ResultSet rs = stmt.executeQuery();

            System.out.println("\n--- CURSOS ENCONTRADOS ---");
            while (rs.next()) {
                System.out.println("Curso: " + rs.getString("nome_curso")
                        + " | Duração: " + rs.getInt("duracao") + " semestres"
                        + " | Instituição: " + rs.getString("nome"));
            }
        } catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    public void filtrarInstituicaoPorNome(String nome) {
        String sql = "SELECT * FROM instituicao WHERE nome ILIKE ?";
        try (Connection conn = Conexão.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nome + "%");
            ResultSet rs = stmt.executeQuery();
            System.out.println("\n--- INSTITUIÇÕES ENCONTRADAS ---");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id_instituicao") + " | Nome: " + rs.getString("nome") + " | CNPJ: " + rs.getString("cnpj"));
            }
        } catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    public void listarTodasInstituicoes() {
        String sql = "SELECT * FROM instituicao ORDER BY id_instituicao ASC";

        try (Connection conn = Conexão.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n========== LISTA DE INSTITUIÇÕES ==========");
            boolean temDados = false;
            while (rs.next()) {
                temDados = true;
                System.out.printf("ID: %d | Nome: %s | CNPJ: %s | End: %s\n",
                        rs.getInt("id_instituicao"),
                        rs.getString("nome"),
                        rs.getString("cnpj"),
                        rs.getString("endereco"));
            }
            if (!temDados) {
                System.out.println("Nenhuma instituição cadastrada.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar instituições: " + e.getMessage());
        }
    }

    public void listarTodosCursos() {
        String sql = "SELECT c.id_curso, c.nome_curso, c.duracao, i.nome AS nome_inst "
                + "FROM curso c "
                + "LEFT JOIN instituicao i ON c.fk_id_instituicao = i.id_instituicao "
                + "ORDER BY c.id_curso ASC";

        try (Connection conn = Conexão.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n========== LISTA DE CURSOS ==========");
            boolean temDados = false;
            while (rs.next()) {
                temDados = true;
                System.out.printf("ID: %d | Curso: %s | Duração: %d sem. | Instituição: %s\n",
                        rs.getInt("id_curso"),
                        rs.getString("nome_curso"),
                        rs.getInt("duracao"),
                        rs.getString("nome_inst"));
            }
            if (!temDados) {
                System.out.println("Nenhum curso cadastrado.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar cursos: " + e.getMessage());
        }
    }

    public void listarProfessoresEInstituicoes() {
        String sql = "SELECT p.nome AS nome_prof, i.nome AS nome_inst "
                + "FROM instituicao i "
                + "RIGHT JOIN professor p ON p.fk_id_instituicao = i.id_instituicao";

        try (Connection conn = Conexão.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n--- PROFESSORES ---");

            while (rs.next()) {
                String prof = rs.getString("nome_prof");
                String inst = rs.getString("nome_inst");

                if (inst == null) {
                    inst = "NÃO VINCULADO A NENHUMA INSTITUIÇÃO";
                }

                System.out.println("Professor: " + prof + " | Instituição: " + inst);
            }

        } catch (SQLException e) {
            System.err.println("Erro na consulta: " + e.getMessage());
        }
    }

    public void filtrarProfessorPorMateria(String materia) {
        String sql = "SELECT * FROM professor WHERE materias ILIKE ?";
        try (Connection conn = Conexão.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + materia + "%");
            ResultSet rs = stmt.executeQuery();
            System.out.println("\n--- PROFESSORES POR MATÉRIA ---");
            while (rs.next()) {
                System.out.println("Nome: " + rs.getString("nome") + " | Matérias: " + rs.getString("materias"));
            }
        } catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    public void apagarInstituicao(int id) {
        String sql = "DELETE FROM instituicao WHERE id_instituicao = ?";
        try (Connection conn = Conexão.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                System.out.println("Instituição apagada com sucesso!");
            } else {
                System.out.println("Nenhuma instituição encontrada com esse ID.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao apagar: " + e.getMessage());
        }
    }

    public void atualizarInstituicao(int id, String novoNome, String novoEndereco) {
        String sql = "UPDATE instituicao SET nome = ?, endereco = ? WHERE id_instituicao = ?";
        try (Connection conn = Conexão.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, novoNome);
            stmt.setString(2, novoEndereco);
            stmt.setInt(3, id);
            stmt.executeUpdate();
            System.out.println("Instituição atualizada!");
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar: " + e.getMessage());
        }
    }

    public void atualizarCurso(int idCurso, String novoNome, int novaDuracao) {
        String sql = "UPDATE curso SET nome_curso = ?, duracao = ? WHERE id_curso = ?";
        try (Connection conn = Conexão.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, novoNome);
            stmt.setInt(2, novaDuracao);
            stmt.setInt(3, idCurso);
            stmt.executeUpdate();
            System.out.println("Curso atualizado!");
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar curso: " + e.getMessage());
        }
    }
}
