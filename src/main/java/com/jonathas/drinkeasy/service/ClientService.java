package com.jonathas.drinkeasy.service;

import com.jonathas.drinkeasy.exceptions.ClientNotFoundException;
import com.jonathas.drinkeasy.model.dto.ClientDTO;
import com.jonathas.drinkeasy.model.entity.Client;
import com.jonathas.drinkeasy.repository.ClientRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public List<ClientDTO> findAll(){
        List<Client> clients = clientRepository.findAll();

        return clients.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ClientDTO> findById(@NotNull UUID id){
        if(id == null) {
            throw new IllegalArgumentException("The given id must not be null");
        }
        Optional<Client> client = clientRepository.findById(id);
        if(!client.isPresent()){
            throw new ClientNotFoundException("Client not found!");
        }
        return Optional.of(convertToDTO(client.get()));
    }

    @Transactional
    public ClientDTO update(UUID id, ClientDTO clientDTO){
        Optional<Client> optionalClient = clientRepository.findById(id);

        if(!optionalClient.isPresent()){
            throw new ClientNotFoundException("Client not found");
        }

        Client existingClient = optionalClient.get();

        existingClient.setName(clientDTO.getName());
        existingClient.setType(clientDTO.getType());
        existingClient.setAddress(clientDTO.getAddress());
        existingClient.setPhone(clientDTO.getPhone());
        existingClient.setDocument(clientDTO.getDocument());


        Client updatedClient = clientRepository.save(existingClient);

        return convertToDTO(updatedClient);
    }

    @Transactional
    public void delete(UUID id){
        Optional<Client> client = clientRepository.findById(id);

        if(!client.isPresent()){
            throw new ClientNotFoundException("Client not found");
        }
        clientRepository.delete(client.get());
    }

    @Transactional
    public ClientDTO save(ClientDTO clientDTO){
       Client client = create(clientDTO);

       Client savedClient = clientRepository.save(client);

       return convertToDTO(savedClient);
    }

    public ClientDTO convertToDTO(Client client){
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(client.getId());
        clientDTO.setName(client.getName());
        clientDTO.setType(client.getType());
        clientDTO.setAddress(client.getAddress());
        clientDTO.setPhone(client.getPhone());
        clientDTO.setDocument(client.getDocument());

        return clientDTO;
    }

    public Client create(ClientDTO clientDTO){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(clientDTO, Client.class);
    }
}
