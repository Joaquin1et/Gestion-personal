package model;

import java.applet.AudioClip;

/**
 * @author Joaquín
 */
public class Sonido {
    
    AudioClip accesoIncorrecto;
    AudioClip noAutorizadoEliminarEmpleado;
    AudioClip noAutorizadoActualizarEmpleado;
    AudioClip accesoNoAutorizado;
    AudioClip noAutorizadoGuardar;
    AudioClip noAutorizadoEliminarPedido;
    AudioClip noAutorizadoModificarPedido;
    AudioClip noAutorizadoEliminarProducto;
    AudioClip noAutorizadoModificarProducto;
    
    public void SonidoErrorInicio(){
        
        accesoIncorrecto = java.applet.Applet.newAudioClip(getClass().getResource("/musica/Acceso_incorrecto.wav"));
        accesoIncorrecto.play();
        
    }
    
    public void SonidoNoAutorizadoEliminarEmpleado(){
        
        noAutorizadoEliminarEmpleado = java.applet.Applet.newAudioClip(getClass().getResource("/musica/noAutorizadoEliminarEmpleado.wav"));
        noAutorizadoEliminarEmpleado.play();
    
  }
    
    public void SonidoNoAutorizadoActualizarEmpleado(){
        
        noAutorizadoActualizarEmpleado = java.applet.Applet.newAudioClip(getClass().getResource("/musica/noAutorizadoActualizarEmpleados.wav"));
        noAutorizadoActualizarEmpleado.play();
    
  }
    
    public void SonidoNoAutorizadoEntrar(){
        
        accesoNoAutorizado = java.applet.Applet.newAudioClip(getClass().getResource("/musica/accesoDenegadoEntrar.wav"));
        accesoNoAutorizado.play();
    
  }
    public void SonidonoAutorizadoGuardar(){
        
        noAutorizadoGuardar = java.applet.Applet.newAudioClip(getClass().getResource("/musica/noAutorizadoGuardar.wav"));
        noAutorizadoGuardar.play();
        
    }
    
    public void SonidonoAutorizadoEliminarPedido(){
        
        noAutorizadoEliminarPedido = java.applet.Applet.newAudioClip(getClass().getResource("/musica/noAutorizadoEliminarPedido.wav"));
        noAutorizadoEliminarPedido.play();
        
    }
        
    public void SonidonoAutorizadoModificarPedido(){
        
        noAutorizadoModificarPedido = java.applet.Applet.newAudioClip(getClass().getResource("/musica/noAutorizadoModificarPedido.wav"));
        noAutorizadoModificarPedido.play();
        
    } 
    
    public void SonidonoAutorizadoModificarProducto(){
        
        noAutorizadoModificarProducto = java.applet.Applet.newAudioClip(getClass().getResource("/musica/noAutorizadoModificarProducto.wav"));
        noAutorizadoModificarProducto.play();
        
    }
    public void SonidonoAutorizadoEliminarProducto(){
        
        noAutorizadoEliminarProducto = java.applet.Applet.newAudioClip(getClass().getResource("/musica/noAutorizadoEliminarProducto.wav"));
        noAutorizadoEliminarProducto.play();
        
    }
    
}