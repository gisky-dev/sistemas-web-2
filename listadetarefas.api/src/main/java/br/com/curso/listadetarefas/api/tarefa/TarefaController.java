package br.com.curso.listadetarefas.api.tarefa;

// Novas importações
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    private final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @GetMapping
    public List<Tarefa> listarTarefas() {
        return tarefaService.listarTodas();
    }

    // NOVO MÉTODO
    @PostMapping // Mapeia requisições HTTP POST
    @ResponseStatus(HttpStatus.CREATED) // Retorna o status 201 Created em caso de sucesso
    public Tarefa criarTarefa(@RequestBody Tarefa tarefa) {
        // @RequestBody indica que os dados da tarefa virão no corpo da requisição
        return tarefaService.criarTarefa(tarefa);
    }

    // NOVO MÉTODO
    @PutMapping("/{id}") // Mapeia requisições HTTP PUT para /tarefas/{id}
    public ResponseEntity<Tarefa> atualizarTarefa(@PathVariable Long id, @RequestBody Tarefa tarefa) {
        // @PathVariable extrai o 'id' da URL
        return tarefaService.atualizarTarefa(id, tarefa)
                .map(tarefaAtualizada -> ResponseEntity.ok(tarefaAtualizada))
                .orElse(ResponseEntity.notFound().build()); // Retorna 404 se não encontrar
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable Long id) {
        if (tarefaService.deletarTarefa(id)) {
            return ResponseEntity.noContent().build(); // Retorna 204 No Content (sucesso, sem corpo)
        }
        return ResponseEntity.notFound().build(); // Retorna 404 Not Found
    }
}
