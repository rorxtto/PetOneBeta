package app.petone.service;
import app.petone.model.Logs;
import app.petone.repository.LogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LogService {

    @Autowired
    private LogsRepository logsRepository;

    public List<Logs> listAll(){
        return logsRepository.findAll();
    }

    public void Created(String origem, String descricao, String email){
        Logs NewLog = new Logs();
        NewLog.setAcao("adicionado");
        NewLog.setOrigem(origem);
        NewLog.setDescricao(descricao);
        NewLog.setUsuario(email);
        NewLog.setTimestamp(new Date());
        this.logsRepository.save(NewLog);
    }

    public void Updated(String origem, String descricao, String email){
        Logs NewLog = new Logs();
        NewLog.setAcao("atualizado");
        NewLog.setOrigem(origem);
        NewLog.setDescricao(descricao);
        NewLog.setUsuario(email);
        NewLog.setTimestamp(new Date());
        this.logsRepository.save(NewLog);
    }

    public void Deleted(String origem, String descricao, String email){
        Logs NewLog = new Logs();
        NewLog.setAcao("removido");
        NewLog.setOrigem(origem);
        NewLog.setDescricao(descricao);
        NewLog.setUsuario(email);
        NewLog.setTimestamp(new Date());
        this.logsRepository.save(NewLog);
    }

}