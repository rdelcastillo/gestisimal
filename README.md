# GESTISIMAL

Aplicación para el control de artículos de un almacén. Como su uso es didáctico las opciones son muy limitadas.

Las principales clases son:

  * **Clase Artículo** que representa a los artículos del almacén.

    * Su estado será: código, nombre, marca, precio de compra, precio de venta, número de unidades (nunca negativas), stock de seguridad y stock máximo (si no proporcionamos los valores de stock valen cero, igualmente si no proporcionamos el del stock máximo).

    * Como comportamiento: consideramos que el código va a generarse de forma automática en el constructor, así no puede haber dos artículos con el mismo código. Esto implica que no puede modificarse el código de un artículo, sí el resto de las propiedades. Podremos mostrar el artículo, por lo que necesito una representación del artículo en forma de cadena (toString).

  * **Clase Almacén** que realice el alta, baja, modificación, entrada de mercancía (incrementa unidades), salida de mercancía (decrementa unidades).

    * El estado será un ArrayList de artículos (pero la clase no será un ArrayList).

    * Su comportamiento será: añadir artículos (no puede haber dos artículos con el mismo nombre y marca), eliminar artículos, incrementar las existencias de un artículo (se delega en la clase Artículo),  decrementar las existencias de un artículo (nunca por debajo de cero, se delega en la clase Artículo), devolver un artículo (para mostrarlo) y los métodos asociados a la persistencia.

Hay más clases, te invito a que las explores y propongas mejoras.