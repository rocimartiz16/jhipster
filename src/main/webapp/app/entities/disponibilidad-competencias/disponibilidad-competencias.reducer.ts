import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IDisponibilidadCompetencias, defaultValue } from 'app/shared/model/disponibilidad-competencias.model';

export const ACTION_TYPES = {
  FETCH_DISPONIBILIDADCOMPETENCIAS_LIST: 'disponibilidadCompetencias/FETCH_DISPONIBILIDADCOMPETENCIAS_LIST',
  FETCH_DISPONIBILIDADCOMPETENCIAS: 'disponibilidadCompetencias/FETCH_DISPONIBILIDADCOMPETENCIAS',
  CREATE_DISPONIBILIDADCOMPETENCIAS: 'disponibilidadCompetencias/CREATE_DISPONIBILIDADCOMPETENCIAS',
  UPDATE_DISPONIBILIDADCOMPETENCIAS: 'disponibilidadCompetencias/UPDATE_DISPONIBILIDADCOMPETENCIAS',
  DELETE_DISPONIBILIDADCOMPETENCIAS: 'disponibilidadCompetencias/DELETE_DISPONIBILIDADCOMPETENCIAS',
  RESET: 'disponibilidadCompetencias/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IDisponibilidadCompetencias>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type DisponibilidadCompetenciasState = Readonly<typeof initialState>;

// Reducer

export default (state: DisponibilidadCompetenciasState = initialState, action): DisponibilidadCompetenciasState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_DISPONIBILIDADCOMPETENCIAS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_DISPONIBILIDADCOMPETENCIAS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_DISPONIBILIDADCOMPETENCIAS):
    case REQUEST(ACTION_TYPES.UPDATE_DISPONIBILIDADCOMPETENCIAS):
    case REQUEST(ACTION_TYPES.DELETE_DISPONIBILIDADCOMPETENCIAS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_DISPONIBILIDADCOMPETENCIAS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_DISPONIBILIDADCOMPETENCIAS):
    case FAILURE(ACTION_TYPES.CREATE_DISPONIBILIDADCOMPETENCIAS):
    case FAILURE(ACTION_TYPES.UPDATE_DISPONIBILIDADCOMPETENCIAS):
    case FAILURE(ACTION_TYPES.DELETE_DISPONIBILIDADCOMPETENCIAS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_DISPONIBILIDADCOMPETENCIAS_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_DISPONIBILIDADCOMPETENCIAS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_DISPONIBILIDADCOMPETENCIAS):
    case SUCCESS(ACTION_TYPES.UPDATE_DISPONIBILIDADCOMPETENCIAS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_DISPONIBILIDADCOMPETENCIAS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/disponibilidad-competencias';

// Actions

export const getEntities: ICrudGetAllAction<IDisponibilidadCompetencias> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_DISPONIBILIDADCOMPETENCIAS_LIST,
    payload: axios.get<IDisponibilidadCompetencias>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IDisponibilidadCompetencias> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_DISPONIBILIDADCOMPETENCIAS,
    payload: axios.get<IDisponibilidadCompetencias>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IDisponibilidadCompetencias> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_DISPONIBILIDADCOMPETENCIAS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IDisponibilidadCompetencias> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_DISPONIBILIDADCOMPETENCIAS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IDisponibilidadCompetencias> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_DISPONIBILIDADCOMPETENCIAS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
