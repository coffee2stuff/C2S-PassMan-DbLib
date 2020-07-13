package models

/**
 * Base model from which other data classes need to extend in order to carry the information
 * about their type.
 */
abstract class BaseModel {
    /**
     * This method is required to pass the information about the model type: this
     * has to be performed manually for each new model added
     *
     * @return integer value representing the model type
     */
    abstract fun modelType(): Int
}