package edu.poly.shop.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Service;

import edu.poly.shop.domain.Account;
import edu.poly.shop.repository.AccountRepository;
import edu.poly.shop.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	AccountRepository accountRepository;
	
	

	public AccountServiceImpl(AccountRepository accountRepository) {
		
		this.accountRepository = accountRepository;
	}


	
	


	@Override
	public Page<Account> findByUsernameContaining(String username, Pageable pageable) {
		return accountRepository.findByUsernameContaining(username, pageable);
	}






	public <S extends Account> S save(S entity) {
		return accountRepository.save(entity);
	}


	public <S extends Account> List<S> saveAll(Iterable<S> entities) {
		return accountRepository.saveAll(entities);
	}





	public List<Account> findAll(Sort sort) {
		return accountRepository.findAll(sort);
	}


	public void flush() {
		accountRepository.flush();
	}

	
	public Page<Account> findAll(Pageable pageable) {
		return accountRepository.findAll(pageable);
	}

	
	public <S extends Account> S saveAndFlush(S entity) {
		return accountRepository.saveAndFlush(entity);
	}

	
	public <S extends Account> List<S> saveAllAndFlush(Iterable<S> entities) {
		return accountRepository.saveAllAndFlush(entities);
	}

	
	public List<Account> findAll() {
		return accountRepository.findAll();
	}


	public List<Account> findAllById(Iterable<String> ids) {
		return accountRepository.findAllById(ids);
	}


	public void deleteInBatch(Iterable<Account> entities) {
		accountRepository.deleteInBatch(entities);
	}

	
	public <S extends Account> Page<S> findAll(Example<S> example, Pageable pageable) {
		return accountRepository.findAll(example, pageable);
	}

	public Optional<Account> findById(String id) {
		return accountRepository.findById(id);
	}

	
	public void deleteAllInBatch(Iterable<Account> entities) {
		accountRepository.deleteAllInBatch(entities);
	}


	public boolean existsById(String id) {
		return accountRepository.existsById(id);
	}

	

	
	public void deleteAllByIdInBatch(Iterable<String> ids) {
		accountRepository.deleteAllByIdInBatch(ids);
	}



	
	public void deleteAllInBatch() {
		accountRepository.deleteAllInBatch();
	}

	public Account getOne(String id) {
		return accountRepository.getOne(id);
	}


	public long count() {
		return accountRepository.count();
	}


	public void deleteById(String id) {
		accountRepository.deleteById(id);
	}


	public Account getById(String id) {
		return accountRepository.getById(id);
	}

	public void delete(Account entity) {
		accountRepository.delete(entity);
	}


	public void deleteAllById(Iterable<? extends String> ids) {
		accountRepository.deleteAllById(ids);
	}


	public Account getReferenceById(String id) {
		return accountRepository.getReferenceById(id);
	}


	public void deleteAll(Iterable<? extends Account> entities) {
		accountRepository.deleteAll(entities);
	}

	


	public void deleteAll() {
		accountRepository.deleteAll();
	}


	@Override
	public <S extends Account> List<S> findAll(Example<S> example, Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public <S extends Account> List<S> findAll(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public <S extends Account, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public <S extends Account> boolean exists(Example<S> example) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public <S extends Account> long count(Example<S> example) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public <S extends Account> Optional<S> findOne(Example<S> example) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}



	
	
	
}
