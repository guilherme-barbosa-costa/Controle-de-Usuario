/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.bean.Usuarios;

/**
 *
 * @author Senai
 */
public class UsuariosDAO {
    public Usuarios logar (String email, String senha){
           Usuarios user = new Usuarios();
        try{
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            
            stmt = conexao.prepareStatement("SELECT * FROM usuarios WHERE email = ? AND senha = ?");
            stmt.setString(1, email);
            stmt.setString(2, senha);
            
            rs = stmt.executeQuery();
            
            if(rs.next()){
                user.setId(rs.getInt("id"));
                user.setNome(rs.getString("nome"));
                user.setSobrenome(rs.getString("sobrenome"));
                user.setStatus(rs.getString("status")); 
            }
            rs.close();
            stmt.close();
            conexao.close();
            
            
        }catch(SQLException e){
            e.printStackTrace();
        }return user;
    }
}
