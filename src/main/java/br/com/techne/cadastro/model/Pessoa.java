package br.com.techne.cadastro.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

import biz.r2s.core.crud.model.BaseModel;
import biz.r2s.scaffolding.annotation.MenuScaffold;
import biz.r2s.scaffolding.annotation.Scaffolding;

@Entity
@Scaffolding
@MenuScaffold(key = "", root = "")
public class Pessoa implements BaseModel<Long> {
	@Id
	@GeneratedValue
	private Long id;
	private String nome;
	@Version
	private Long version;
	private String cpf;
	private int idade;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public int getIdade() {
		return idade;
	}
	public void setIdade(int idade) {
		this.idade = idade;
	}
}
